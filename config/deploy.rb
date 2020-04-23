# config valid only for current version of Capistrano
lock '3.6.1'

set :application, 'leadpath'
set :repo_url, 'git@github.com:TapQuality/Leadpath.git'

# Default branch is :master
ask :branch, `git rev-parse --abbrev-ref HEAD`.chomp

# Default deploy_to directory is /var/www/my_app_name
set :deploy_to, "/var/cap/#{ fetch(:application) }"

# Default value for :scm is :git
set :scm, :git

# Default value for :format is :airbrussh.
# set :format, :airbrussh

# You can configure the Airbrussh format using :format_options.
# These are the defaults.
# set :format_options, command_output: true, log_file: 'log/capistrano.log', color: :auto, truncate: :auto

# Default value for :pty is false
# set :pty, true

# Default value for :linked_files is []
# append :linked_files, 'config/database.yml', 'config/secrets.yml'

# Default value for linked_dirs is []
# append :linked_dirs, 'log', 'tmp/pids', 'tmp/cache', 'tmp/sockets', 'public/system'

# Default value for default_env is {}
# set :default_env, { path: "/opt/ruby/bin:$PATH" }

# Default value for keep_releases is 5
# set :keep_releases, 5

namespace :deploy do
  task :compile do
    on roles(:leadpath) do
      execute "pushd #{current_path}/src/webcontents && bower install && popd"
      execute "pushd #{current_path} && mvn package -DskipTests && popd"
      execute "curl --upload-file #{current_path}/target/leadpath-1.0-SNAPSHOT.war \"http://tapquality:TapQuality1@localhost:8080/manager/text/deploy?path=/ROOT&update=true\" 2> /dev/null"
    end
  end
  
  after "deploy:published", "deploy:compile"
end
