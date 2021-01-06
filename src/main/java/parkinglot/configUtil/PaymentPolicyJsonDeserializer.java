package parkinglot.configUtil;

import com.google.gson.*;
import parkinglot.policy.PaymentPolicy;

import java.lang.reflect.Type;

/**
 * @author 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
public class PaymentPolicyJsonDeserializer implements JsonDeserializer<PaymentPolicy> {
    @Override
    public PaymentPolicy deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return PaymentPolicy.PaymentPerTimeWithMinimumTimePolicy(
                jsonObject.get("baseTime").getAsInt(),
                jsonObject.get("baseFee").getAsInt(),
                jsonObject.get("rate").getAsInt(),
                jsonObject.get("per").getAsInt()
        );
    }
}
