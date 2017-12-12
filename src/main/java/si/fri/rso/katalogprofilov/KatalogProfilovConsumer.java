package si.fri.rso.katalogprofilov;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.streaming.common.annotations.StreamListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Log
public class KatalogProfilovConsumer {

    private Logger log = LogManager.getLogger(KatalogProfilovConsumer.class.getName());

    @StreamListener(topics = {"xtmm0ew0-default"})
    public void onMessage(ConsumerRecord<String, String> record) {

        log.info(String.format("Consumed message: offset = %d, key = %s, value = %s%n", record.offset(), record.key()
                , record.value()));

        JSONObject message = new JSONObject(record.value());


        log.info("Status for order " + message.getString("id") + " set to " + message.getString("status"));

    }
}
