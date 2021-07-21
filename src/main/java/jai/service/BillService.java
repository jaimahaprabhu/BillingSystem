package jai.service;

import jai.entity.BillOrder;

import java.util.List;

public interface BillService {

	public List<BillOrder> generateBill(int orderId);

	public List<BillOrder> printBill(int orderId);

	public boolean saveBill(int orderId);

	public float getTotal();

}
