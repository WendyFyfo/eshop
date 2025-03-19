package enums;

public enum PaymentMethod {
    VOUCHER_CODE,
    BANK_TRANSFER;

    public static boolean contains(String value) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}

