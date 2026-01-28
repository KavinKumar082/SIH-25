package com.example.backend.dto.request.customer;

public class SearchFilterRequest {

    /**
     * Can be:
     * - Customer Name (Arun)
     * - Customer ID (numeric)
     * - City (Delhi)
     */
    private String searchTerm;

    /**
     * Field to sort by (example: customerName, customerId)
     */
    private String sortField;

    /**
     * asc | desc
     */
    private String sortOrder;

    /**
     * Page number (0-based)
     */
    private Integer page;

    /**
     * Page size (10, 20, 50...)
     */
    private Integer pageSize;

    /**
     * Optional city filter
     */
    private String city;

    // ---------- Getters & Setters ----------

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
