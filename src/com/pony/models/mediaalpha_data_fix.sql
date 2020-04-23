START TRANSACTION;

UPDATE lead_matches
        JOIN leads ON lead_matches.lead_id = leads.id
        LEFT OUTER JOIN profile_attributes ON leads.user_profile_id = profile_attributes.user_profile_id AND attribute_id = 855
    SET lead_matches.external_id = profile_attributes.value
    WHERE order_id = 23;

COMMIT;
