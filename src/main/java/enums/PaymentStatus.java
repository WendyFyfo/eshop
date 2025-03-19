package enums;

public enum PaymentStatus {
    SUCCESS,
    REJECTED;

    public static boolean contains(String value) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
