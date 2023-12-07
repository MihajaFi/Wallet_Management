    package models;

    import java.math.BigDecimal;
    import java.sql.Date;
    import java.time.LocalDateTime;

    public class Transaction {
        private int id;
        private String label;
        private BigDecimal amount;
        private LocalDateTime date;
        private TransactionType type;
        private String idAccount ;


        public Transaction(int id, String label, BigDecimal amount, LocalDateTime date, TransactionType type,String idAccount) {
            this.id = id;
            this.label = label;
            this.amount = amount;
            this.date = date;
            this.type = type;
            this.idAccount = idAccount ;
        }

        public Transaction(String label, BigDecimal amount, LocalDateTime date, TransactionType type, String idAccount) {
            this.label = label;
            this.amount = amount;
            this.date = date;
            this.type = type;
            this.idAccount = idAccount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public TransactionType getType() {
            return type;
        }

        public void setType(TransactionType type) {
            this.type = type;
        }

        public String getIdAccount() {
            return idAccount;
        }

        public void setIdAccount(String idAccount) {
            this.idAccount = idAccount;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "id=" + id +
                    ", label='" + label + '\'' +
                    ", amount=" + amount +
                    ", date=" + date +
                    ", type=" + type +
                    '}';
        }
    }
