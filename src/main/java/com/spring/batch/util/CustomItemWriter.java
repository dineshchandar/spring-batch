package com.spring.batch.util;

import com.spring.batch.model.Birthday;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

import javax.sql.DataSource;
import java.util.List;

public class CustomItemWriter implements ItemWriter<Birthday> {

    DataSource ds;

    public CustomItemWriter(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void write(List<? extends Birthday> list) throws Exception {



//        JdbcBatchItemWriter iw = new JdbcBatchItemWriter();
//
//        for (Birthday birthday : list) {
//
//            String consentId = birthday.getConsentId().toString();
//            String lastReminderStatus = consentRec.getLastReminderStatus();
//
//            String query = "UPDATE consent_revised " + "SET last_reminder_sent = " +
//                    "CURRENT_TIMESTAMP, last_reminder_status = ? where consent_id = ?";
//
//            iw = new JdbcBatchItemWriterBuilder<consent>().dataSource(ds).sql(query)
//                                                          .itemPreparedStatementSetter((consent, preparedStatement) -> {
//                                                              preparedStatement
//                                                                      .setString(2,
//                                                                              consentId);
//                                                              preparedStatement
//                                                                      .setString(1,
//                                                                              "REQUEST " +
//                                                                                      "SENT");
//                                                              preparedStatement
//                                                                      .executeUpdate();
//                                                          }).build();
//        }
//        iw.write(list);
    }
}
