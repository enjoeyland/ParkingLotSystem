package parkinglot.data;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author 202020790 민경현(Min Kyung Hyun)
 * contact : khmin1104@gmail.com
 */
@Getter
public abstract class Vehicle {
    private final String licensePlateNum;

    public Vehicle(@NonNull String licensePlateNum) {
        this.licensePlateNum = licensePlateNum;
    }
}
