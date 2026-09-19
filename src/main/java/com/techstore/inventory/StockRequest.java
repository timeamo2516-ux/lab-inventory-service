package com.techstore.inventory;

import jakarta.validation.constraints.Min;

public record StockRequest(@Min(1) int quantity) {}
