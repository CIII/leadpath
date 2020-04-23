import time, Queue, random, os, sys, time, socket, traceback, paramiko, MySQLdb
#import os, logging
#from database import Database

# requires staging tables: 
# opens:	create table sendplex_opens (host_id int(11) not null, open_id int(11) not null, message_id int(11) not null, open_count int(11) not null, ip_address varchar(50), user_agent varchar(255), referrer varchar(255), created_at datetime, updated_at datetime, primary key(open_id))  ENGINE=InnoDB DEFAULT CHARSET=utf8;
# clicks: 	create table sendplex_clicks(host_id int(11) not null, click_id int(11) not null, message_id int(11) not null, click_count int(11) not null, created_at datetime, updated_at datetime, primary key(click_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8; 
# bounces:	create table sendplex_bounces (host_id int(11) not null, bounce_id int(11) not null, email varchar(255) not null, created_at datetime not null, comment varchar(255), primary key(bounce_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# unsubscribes:	create table sendplex_unsubscribes (host_id int(11) not null, unsub_id int(11) not null, message_id int(11) not null, reason text, created_at datetime, primary key (unsub_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;


class DataSync(object):
    def __init__(self, port=22):
        self.port = port
        self.last_command = time.time()

        self.conn = MySQLdb.connect(host="localhost",
            user="root",
            passwd="okcomputer",
            db="pony_leads")
	self.maincursor = self.conn.cursor()
        self.cursor = self.conn.cursor()

    def sync(self):
        ####Get host...for each....
        self.maincursor.execute(
            "select h.id, h.domain_name from hosts h join smtp_providers s on s.id = h.smtp_provider_id  where s.name = 'doublenickels' and h.status = 0")
        while (1):
            row = self.maincursor.fetchone()

            if row == None:
                break
            else:
                hostid = row[0]
                domainname = row[1]
                print "host id is", hostid
                print "domain is", domainname

                self.hostname = domainname
                self.username = 'root'
                self.password = 'okcomputer'
                self.connect()

                ####get max ids from mysql
                self.cursor.execute(
                    "select max(o.external_id) from opens o join messages m on m.id = o.message_id where m.host_id = %s" % (hostid))
                row = self.cursor.fetchone()
                opensid = row[0]
                if opensid is None:
                    opensid = 0

                self.cursor.execute(
                    "select max(c.external_id) from clicks c join messages m on m.id = c.message_id where m.host_id = %s" % (hostid))
                row = self.cursor.fetchone()
                clicksid = row[0]
                if clicksid is None:
                    clicksid = 0

                self.cursor.execute(
                    "select max(u.external_id) from unsubscribes u join messages m on m.id = u.message_id where m.host_id = %s" % (hostid))
                row = self.cursor.fetchone()
                unsubid = row[0]

                if unsubid is None:
                    unsubid = 0

                self.cursor.execute(
                    "select max(b.external_id) from bounces b join user_profiles up on up.id = b.user_profile_id join messages m on m.user_profile_id = b.user_profile_id where m.host_id = %s" % (hostid))
                row = self.cursor.fetchone()
                bouncesid = row[0]
                if bouncesid is None:
                    bouncesid = 0

                print "openid ", opensid
                print "clickid", clicksid
                print "unsubid", unsubid
                print "bounceid", bouncesid

                print "export remote data"

                print self.run_command("mkdir -p /var/lib/mysql/doublenickels/data_grabs/")
                print self.run_command("chmod 777 /var/lib/mysql/doublenickels/data_grabs/")
                print self.run_command("rm -f /var/lib/mysql/doublenickels/data_grabs/*")

                print self.run_command(
                    "sudo yes okcomputer | mysql -u root -p doublenickels -e \"select o.id, o.message_id, o.open_count, o.ip_address, o.user_agent, o.referrer, o.created_at, o.updated_at into outfile 'doublenickels/data_grabs/opens.csv' FIELDS ENCLOSED BY '\\\"' TERMINATED BY ',' LINES TERMINATED BY '\n'  from opens o join messages m on m.id = o.message_id where m.pony_id > 0 and o.id > %s\"" % (opensid))

                print self.run_command(
                    "sudo yes okcomputer | mysql -u root -p doublenickels -e \"select c.id, c.message_id, c.click_count, c.created_at, c.updated_at  into outfile 'doublenickels/data_grabs/clicks.csv' FIELDS ENCLOSED BY '\\\"' TERMINATED BY ',' LINES TERMINATED BY '\n'  from clicks c join messages m on m.id = c.message_id where m.pony_id > 0 and c.id > %s\"" % (clicksid))

                print self.run_command(
                    "sudo yes okcomputer | mysql -u root -p doublenickels -e \"select u.id, u.message_id, u.reason, u.created_at into outfile 'doublenickels/data_grabs/unsubs.csv' FIELDS ENCLOSED BY '\\\"' TERMINATED BY ',' LINES TERMINATED BY '\n' from unsubscribes u join messages m on m.id = u.message_id where m.pony_id >0 and u.id > %s\"" % (unsubid))

                print self.run_command(
                    "sudo yes okcomputer | mysql -u root -p doublenickels -e \"select b.id, up.email, b.response, b.created_at into outfile 'doublenickels/data_grabs/bounces.csv' FIELDS ENCLOSED BY '\\\"' TERMINATED BY ',' LINES TERMINATED BY '\n'  from bounces b join user_profiles up on up.id = b.user_profile_id and b.id > %s\"" % (bouncesid))

                print "removing old staging files"
                os.system("rm /root/doublenickels/data_grabs_pony/opens_%s.csv" % (hostid))
                os.system("rm /root/doublenickels/data_grabs_pony/clicks_%s.csv" % (hostid))
                os.system("rm /root/doublenickels/data_grabs_pony/bounces_%s.csv" % (hostid))
                os.system("rm /root/doublenickels/data_grabs_pony/unsubs_%s.csv" % (hostid))

                print "copy data"

                self.sftp.get("/var/lib/mysql/doublenickels/data_grabs/opens.csv", "/root/doublenickels/data_grabs_pony/opens_%s.csv" % (hostid))
                self.sftp.get("/var/lib/mysql/doublenickels/data_grabs/clicks.csv", "/root/doublenickels/data_grabs_pony/clicks_%s.csv" % (hostid))
                self.sftp.get("/var/lib/mysql/doublenickels/data_grabs/unsubs.csv", "/root/doublenickels/data_grabs_pony/unsubs_%s.csv" % (hostid))
                self.sftp.get("/var/lib/mysql/doublenickels/data_grabs/bounces.csv", "/root/doublenickels/data_grabs_pony/bounces_%s.csv" % (hostid))

                ####remove remote files
                print self.run_command("rm /var/lib/mysql/doublenickels/data_grabs/opens.csv")
                print self.run_command("rm /var/lib/mysql/doublenickels/data_grabs/clicks.csv")
                print self.run_command("rm /var/lib/mysql/doublenickels/data_grabs/unsubs.csv")
                print self.run_command("rm /var/lib/mysql/doublenickels/data_grabs/bounces.csv")

                print "load data"

                self.cursor.execute("delete from sendplex_opens where host_id = %s" % (hostid))
                self.cursor.execute("load data local infile '/root/doublenickels/data_grabs_pony/opens_%s.csv' into table sendplex_opens fields terminated by ',' enclosed by '\"' lines terminated by '\n' (open_id, message_id, open_count, ip_address, user_agent, referrer, created_at, updated_at) set host_id = %s" % (hostid, hostid))
                self.cursor.execute("insert ignore into opens(select null, m.id, o.open_id, o.open_count, o.ip_address, o.user_agent, o.referrer, now(), o.created_at, o.updated_at from sendplex_opens o join messages m on o.host_id = m.host_id and m.external_id = o.message_id and m.host_id = %s)" % (hostid))
                self.conn.commit();

                self.cursor.execute("delete from sendplex_clicks where host_id = %s" % (hostid))
                self.cursor.execute("load data local infile '/root/doublenickels/data_grabs_pony/clicks_%s.csv' into table sendplex_clicks fields terminated by ',' enclosed by '\"' lines terminated by '\n' (click_id, message_id, click_count, created_at, updated_at) set host_id = %s" % (hostid,hostid))
                self.cursor.execute("insert ignore into clicks(select null, m.id, c.click_id, c.click_count, now(), c.created_at, c.updated_at from sendplex_clicks c join messages m on m.external_id = c.message_id and m.host_id = c.host_id and m.host_id = %s)" % (hostid))
                self.conn.commit()

                self.cursor.execute("delete from sendplex_unsubscribes where host_id = %s" % (hostid))
                self.cursor.execute("load data local infile '/root/doublenickels/data_grabs_pony/unsubs_%s.csv' into table sendplex_unsubscribes fields terminated by ',' enclosed by '\"' lines terminated by '\n' (unsub_id, message_id,reason, created_at) set host_id = %s" % (hostid,hostid))
                self.cursor.execute("insert ignore into unsubscribes (select null, up.id, m.id, u.unsub_id, null, null, null, now(), u.created_at from sendplex_unsubscribes u join messages m on m.external_id = u.message_id and m.host_id = u.host_id join user_profiles up on up.id = m.user_profile_id join (select message_id, max(unsub_id) unsub_id from sendplex_unsubscribes where host_id = %s group by message_id)x on x.message_id = m.external_id where m.host_id = %s)" % (hostid,hostid))
                self.conn.commit()

                self.cursor.execute("delete from sendplex_bounces where host_id=%s" % (hostid))
                self.cursor.execute("load data local infile '/root/doublenickels/data_grabs_pony/bounces_%s.csv' into table sendplex_bounces fields terminated by ',' enclosed by '\"' lines terminated by '\n' (bounce_id, email, comment, created_at)  set host_id = %s" % (hostid,hostid))
                self.cursor.execute("insert ignore into bounces (select null, up.id, null, b.host_id, b.bounce_id, null, comment, now(), b.created_at from sendplex_bounces b join user_profiles up on up.email = b.email and b.host_id = %s)" % (hostid))
                pattern = "SMTP; %"
                self.cursor.execute("update bounces set status_code = substring(message,7,3), message = substring(message,11) where host_id = %s and message like '%s'" % (hostid, pattern))
                self.conn.commit()


        ###close dat der connection
        self.close()

    def get_hostname(self):
        return self.hostname


    def connect(self):
        self.ssh = paramiko.SSHClient()
        self.ssh.load_system_host_keys()
        self.ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

        # print "hostname is", self.hostname
        # print "username is", self.username
        # print "password is", self.password
        # print "port is", self.port
        try:
            print "trying to connect"
            self.ssh.connect(self.hostname, username=self.username, password=self.password, port=self.port, timeout=10)
            self.sftp = self.ssh.open_sftp()
        except:
            print "connection failed"
            raise


    def check_idle_time(self):
        if time.time() - self.last_command > 5 * 60: #greater than 5 minutes
            self.close()
            time.sleep(1)
            self.connect()
        self.last_command = time.time()


    def remove_from_host(self, filename):
        command = """rm %s""" % (filename)
        return self.run_command(command)


    def run_command(self, cmd, close_socket=True):
        self.check_idle_time()
        stdin, stdout, stderr = self.ssh.exec_command(cmd)
        return stdout.read()


    def scp_get_file(self, dir, filedest):
        dest = "/root/doublenickels/data_grabs_pony/%s" % (filedest)

        self.sftp.get(filedest, dest)
        ####test that file was correctly scraped + scped
        #file_size = os.path.getsize(dest)
        #if (file_size < 100):
        #	print "scp_get_file filesize < 100"
        #	return False
        #else:
        #	return True


    def getSize(self, file):
        self.checkIdleTime()
        return self.sftp.stat(file).st_size


    def close(self):
        self.sftp.close()
        self.ssh.close()

if __name__ == "__main__":
    data_syncer = DataSync()
    data_syncer.sync()

