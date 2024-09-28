package services;
//interface for the functions
public interface OnlinePayment {
	
	double paymentFee(double amount);
	double interest(double amount, int months);
}
