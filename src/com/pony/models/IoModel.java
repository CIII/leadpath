package com.pony.models;

import com.pony.advertiser.Io;
import com.pony.core.PonyPhase;
import com.pony.lead.LeadMatch;
import com.pony.lead.LeadType;
import com.pony.publisher.PublisherList;
import com.pony.validation.*;
import com.pony.validation.whitepages.IWhitepagesFilter;
import com.pony.validation.whitepages.IWhitepagesFilterFactory;
import com.pony.validation.whitepages.WhitepagesAcceptFilter;
import com.pony.validation.whitepages.WhitepagesFilterFactory;
import com.tapquality.db.tables.records.OrdersRecord;

import static com.tapquality.db.tables.Orders.ORDERS;

import javax.naming.NamingException;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 9:19 PM
 */
public class IoModel extends Model {
    private static final String COLUMNS = "o.id, o.code, o.advertiser_id, o.lead_type_id, o.vpl, o.status, o.cap_daily, o.cap_monthly, o.cap_total, o.source_id, o.is_exclusive, o.pixel_id, o.target_url, o.weight, o.email";

    private Io io;

    private IoModel(Long leadTypeId, ResultSet rs) throws SQLException {
        super(rs.getLong("id"));
        io = Io.create(leadTypeId, rs);
    }
    
    public IoModel(Io order) {
    	super(order.getId());
    	io = order;
    }

    public static IoModel findByDispositionId(Long dispositionId) throws NamingException, SQLException, ValidationException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from orders o join lead_matches lm on lm.order_id=o.id join advertiser_dispositions d on d.lead_match_id = lm.id where d.id=?");
            stmt.setLong(1, dispositionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new IoModel(rs.getLong("lead_type_id"), rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static IoModel findByLeadMatchId(Long leadMatchId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from orders o join lead_matches lm on lm.order_id=o.id join advertiser_dispositions d on d.lead_match_id = lm.id where lm.id=?");
            stmt.setLong(1, leadMatchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new IoModel(rs.getLong("lead_type_id"), rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static IoModel findByCode(String code, LeadType leadType) throws NamingException, SQLException, ValidationException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from orders o where lead_type_id = ? and status = 1 and code = ?");
            stmt.setLong(1, leadType.getId());
            stmt.setString(2, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new IoModel(leadType.getId(), rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Io findById(Long id) throws NamingException, SQLException, ValidationException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from orders o where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new IoModel(rs.getLong("lead_type_id"), rs).getIo();
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static List<IoModel> findDirect(LeadType leadType, PublisherList publisherList) throws NamingException, SQLException, ValidationException {
        List<IoModel> models = new ArrayList<IoModel>();

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select distinct " + COLUMNS + " from orders o join publisher_list_orders plo on plo.order_id = o.id where o.lead_type_id = ? and o.status = 1 and plo.status = 1 and plo.publisher_list_id = ?");
            stmt.setLong(1, leadType.getId());
            stmt.setLong(2, publisherList.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                models.add(new IoModel(leadType.getId(), rs));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return models;
    }

    public static List<IoModel> findAll(LeadType leadType) throws NamingException, SQLException, ValidationException {
        List<IoModel> models = new ArrayList<IoModel>();

        Result<OrdersRecord> orders = findAll(leadType, Boolean.TRUE);
        for (OrdersRecord record : orders ) {
        	Io order = new Io(
        			record.getLeadTypeId() != null ? new Long(record.getLeadTypeId()) : null,
        			record.getId() != null ? new Long(record.getId()) : null,
        			record.getCode(),
        			record.getAdvertiserId() != null ? new Long(record.getAdvertiserId()) : null,
        			record.getVpl(),
        			record.getStatus() != null ? record.getStatus().intValue() : null,
        			record.getCapDaily() != null ? new Long(record.getCapDaily()) : null,
        			record.getSourceId(), record.getIsExclusive() == 1 ? true : false,
        			record.getPixelId() != null ? new Long(record.getPixelId()) : null,
        			record.getTargetUrl());
        	IoModel orderModel = new IoModel(order);
        	models.add(orderModel);
        }
        
        return models;
    }
    public static Result<OrdersRecord> findAll(LeadType leadType, Boolean status) throws NamingException, SQLException, ValidationException {
    	Result<OrdersRecord> orders;
        Connection con = null;

        
        try {
            con = connectX();
            DSLContext context = DSL.using(con);
            List<Condition> conditions = new ArrayList<>();
            if (leadType != null) {
            	conditions.add(ORDERS.LEAD_TYPE_ID.eq(leadType.getId().intValue()));
            }
            if (status != null) {
            	conditions.add(ORDERS.STATUS.eq((byte) (status ? 1 : 0)));
            }
            orders = context.selectFrom(ORDERS).where(conditions).fetch();
        }
        finally {
            //close(stmt);
            close(con);
        }

        return orders;
    }

    public Io getIo() {
        return io;
    }

    /**
     * check the leads count for today against the daily cap of each io in the list, and filter out those that are at or above the cap
     *
     * @param ios the list to check caps for
     */
    public static void applyCaps(List<IoModel> ios) throws NamingException, SQLException {
        List<IoModel> toRemove = new ArrayList<IoModel>();

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select count(*) cnt from lead_matches where order_id = ? and status = ? and date(created_at) = curdate()");
            stmt.setInt(2, LeadMatch.ACCEPTED.getStatus());
            for (IoModel io : ios) {
                if (io.getIo().getCapDaily() > 0) { // cap=0 == no cap at all
                    stmt.setLong(1, io.getId());
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next() && rs.getLong("cnt") >= io.getIo().getCapDaily()) {
                        toRemove.add(io);
                    }
                }
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        ios.removeAll(toRemove);
    }

    // filters
    public static List<Filter> getFilters(Io io, PonyPhase phase) throws NamingException, SQLException {
        List<Filter> filters = new ArrayList<Filter>();

        Connection con = null;

        try {
            con = connectX();
            filters.addAll(getStateFilters(con, io, phase));
            filters.addAll(getDayFilters(con, io, phase));
            filters.addAll(getTimeFilters(con, io, phase));
            filters.addAll(getZipFilters(con, io, phase));
            filters.addAll(getElectricBillFilters(con, io, phase));
            filters.addAll(getWhitepagesFilters(con, io, phase));
            filters.add(new TestFilter(io, phase));
        }
        finally {
            close(con);
        }

        return filters;
    }

    public static List<Filter> getZipFilters(Connection con, Io io, PonyPhase phase) throws SQLException {
        List<Filter> filters = new ArrayList<Filter>();
        /*
        order_id int(11) not null,
        is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
        zipcodes text not null,
         */
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select is_allow, pony_phase, status, zipcodes from zip_filters where order_id = ? and status = 1 and (pony_phase is null or pony_phase = ?)");
            stmt.setLong(1, io.getId());
            stmt.setInt(2, phase.getCode());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                filters.add(ZipFilter.create(io, rs.getBoolean("is_allow"), rs.getString("zipcodes")));
            }
        }
        finally {
            close(stmt);
        }

        return filters;
    }

    public static List<Filter> getTimeFilters(Connection con, Io io, PonyPhase phase) throws SQLException {
        List<Filter> filters = new ArrayList<Filter>();
        /*
        order_id int(11) not null,
        is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
        times varchar(255) not null,
         */
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select is_allow, pony_phase, status, times from time_filters where order_id = ? and status = 1 and (pony_phase is null or pony_phase = ?)");
            stmt.setLong(1, io.getId());
            stmt.setInt(2, phase.getCode());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                filters.add(TimeFilter.create(io, rs.getBoolean("is_allow"), rs.getString("times")));
            }
        }
        finally {
            close(stmt);
        }

        return filters;
    }

    public static List<Filter> getDayFilters(Connection con, Io io, PonyPhase phase) throws SQLException {
        List<Filter> filters = new ArrayList<Filter>();

        /*
        order_id int(11) not null,
        is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
        days varchar(15) not null,
         */
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select is_allow, pony_phase, status, days from day_filters where order_id = ? and status = 1 and (pony_phase is null or pony_phase = ?)");
            stmt.setLong(1, io.getId());
            stmt.setInt(2, phase.getCode());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                filters.add(DayFilter.create(io, rs.getBoolean("is_allow"), rs.getString("days")));
            }
        }
        finally {
            close(stmt);
        }

        return filters;
    }

    public static List<Filter> getStateFilters(Connection con, Io io, PonyPhase phase) throws NamingException, SQLException {
        List<Filter> filters = new ArrayList<Filter>();

        /*
        order_id int(11) not null,
        is_allow tinyint default 1, pony_phase tinyint, status tinyint default 1,
        states text not null,
         */
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select is_allow, pony_phase, status, states from state_filters where order_id = ? and status = 1 and (pony_phase is null or pony_phase = ?)");
            stmt.setLong(1, io.getId());
            stmt.setInt(2, phase.getCode());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                filters.add(StateFilter.create(io, rs.getBoolean("is_allow"), rs.getString("states")));
            }
        }
        finally {
            close(stmt);
        }

        return filters;
    }
    
    public static List<Filter> getElectricBillFilters(Connection con, Io io, PonyPhase phase) throws SQLException {
    	List<Filter> filters = new ArrayList<Filter>();
    	
    	PreparedStatement stmt = null;
    	try {
    		stmt = con.prepareStatement("SELECT minimum, maximum FROM electric_bill_filters WHERE order_id = ? and (pony_phase IS NULL or pony_phase = ?)");
    		stmt.setLong(1,  io.getId());
    		stmt.setInt(2, phase.getCode());
    		ResultSet rs = stmt.executeQuery();
    		while(rs.next()) {
    			Integer minimum = rs.getInt("minimum");
    			minimum = rs.wasNull() ? null : minimum;
    			Integer maximum = rs.getInt("maximum");
    			maximum = rs.wasNull() ? null : maximum;
    			filters.add(new ElectricBillFilter(minimum, maximum));
    		}
    	} finally {
    		close(stmt);
    	}
    	
    	return filters;
    }
    
    public static List<Filter> getWhitepagesFilters(Connection con, Io io, PonyPhase phase) throws SQLException {
    	List<IWhitepagesFilter> whitepagesFilters = getWhitepagesFilters(con, io, phase, new WhitepagesFilterFactory());
    	// This is a bit of a waste, but it simplifies testing to return List<IWhitepagesFilter> from the inner method.
    	List<Filter> filters = new ArrayList<>();
    	for(IWhitepagesFilter filter : whitepagesFilters) {
    		filters.add((Filter)filter);
    	}
    	
    	return filters;
    }
    
    static List<IWhitepagesFilter> getWhitepagesFilters(Connection con, Io io, PonyPhase phase, IWhitepagesFilterFactory whitepagesFilterFactory) throws SQLException {
    	List<IWhitepagesFilter> filters = new ArrayList<IWhitepagesFilter>();
    	
    	PreparedStatement stmt = null;
    	try {
    		stmt = con.prepareStatement("SELECT parameter_name, accept_value, reject_value FROM whitepages_filters WHERE order_id = ? and (pony_phase IS NULL or pony_phase = ?) ORDER BY parameter_name, ISNULL(accept_value)");
    		stmt.setLong(1,  io.getId());
    		stmt.setInt(2,  phase.getCode());
    		ResultSet rs = stmt.executeQuery();
    		String parameterName = null;
    		
			IWhitepagesFilter newFilter = null;
    		while(rs.next()) {
    			String newParameterName = rs.getString("parameter_name");
    			if(!newParameterName.equals(parameterName)) {
    				parameterName = newParameterName;
    				newFilter = null;
    			}
    			String acceptValue = rs.getString("accept_value");
    			boolean acceptValueExists = !rs.wasNull();
    			String rejectValue = rs.getString("reject_value");
    			boolean rejectValueExists = !rs.wasNull();
    			
    			if(acceptValueExists || rejectValueExists) {
    				if(newFilter == null) {
    					newFilter = whitepagesFilterFactory.getWhitepagesFilter(acceptValue, rejectValue, newParameterName);
    					filters.add(newFilter);
    				}
    				if(newFilter == null) throw new NullPointerException("The whitepages filter factory failed to instantiate a filter with the values acceptValue: " + acceptValue + ", rejectValue: " + rejectValue + ". This should never happen.");
    				
    				if(acceptValueExists) {
    					newFilter.addValue(acceptValue);
    				} else if(rejectValueExists && !(newFilter instanceof WhitepagesAcceptFilter)) {
    					newFilter.addValue(rejectValue);
    				} // If rejectValueExists and yet this was instantiated as a WhitepagesAcceptFilter, then there are both accept filters and reject filters for a given parameter name, and we will ignore all reject filters as redundant.
    				
    			} // If !acceptValueExists and !rejectValueExists, the filter definition was empty and should be ignored.
    		}
    	} finally {
    		close(stmt);
    	}
    	return filters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IoModel ioModel = (IoModel) o;

        if (!io.equals(ioModel.io)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return io.hashCode();
    }

    @Override
    public String toString() {
        return "IoModel{io=" + io + '}';
    }
}
