package services;

import entities.Contract;
import entities.Installment;

import java.time.LocalDate;
import java.util.List;

public class ContractService {
    private OnlinePayment payment;

    public ContractService(OnlinePayment payment) {
        this.payment = payment;
    }

    public void processContract(Contract contract, int months) {
        double basicQuota = contract.getTotalValue() / months;

        for (int i = 1; i <= months; i++) {
            double interest = payment.interest(basicQuota, i);
            double fee = payment.paymentFee(basicQuota + interest);
            double installmentAmount = basicQuota + interest + fee;

            LocalDate dueDate = contract.getDate().plusMonths(i);
            Installment installment = new Installment(dueDate, installmentAmount);

            // Check if the installment is due and mark it as paid if it's due
            if (dueDate.isBefore(LocalDate.now())) {
                installment.markAsPaid(); // Mark as paid if it's due before today
            }

            contract.getInstallments().add(installment);
        }
    }

    public List<Installment> getRemainingInstallments(Contract contract) {
        return contract.getInstallments();
    }
}
