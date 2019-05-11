package com.shoesolution.shoesolution;

public class Order {

    private String customer, harga, perfume, quantity, service, status, uniquecode, payment_method;

    public Order() {
    }

    public Order(String customer, String harga, String perfume, String quantity, String service, String status, String uniquecode, String payment_method) {
        this.customer = customer;
        this.harga = harga;
        this.perfume = perfume;
        this.quantity = quantity;
        this.service = service;
        this.status = status;
        this.uniquecode = uniquecode;
        this.payment_method = payment_method;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getPerfume() {
        return perfume;
    }

    public void setPerfume(String perfume) {
        this.perfume = perfume;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
