// src/purchaseService.js
import apiClient from './api';

export const createPurchase = async (purchaseData) => {
  try {
    const response = await apiClient.post('/purchases', purchaseData);
    return response.data;
  } catch (error) {
    throw error;
  }
};
