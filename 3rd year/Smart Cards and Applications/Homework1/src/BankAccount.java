public class BankAccount {
    public String ownerName;
    public String expireDate;
    public int cvv;
    public String cardNumber;
    public String stockMoney;


    public BankAccount(String ownerName, String expireDate, int cvv, String cardNumber, String stockMoney) {
        this.ownerName = ownerName;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.cardNumber = cardNumber;
        this.stockMoney = stockMoney;
    }
}
