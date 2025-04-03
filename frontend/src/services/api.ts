import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
  withCredentials: true // Habilitar el envío de cookies entre dominios
});

export interface PaymentRequest {
  amount: number;
  paymentMethod: string;
}

export interface PaymentResponse {
  transactionId: string;
  success: boolean;
  message: string;
  paymentMethod: string;
}

export const paymentService = {
  processPayment: async (data: PaymentRequest): Promise<PaymentResponse> => {
    try {
      const response = await api.post<PaymentResponse>('/payments/process', data);
      return response.data;
    } catch (error) {
      console.error('Error al procesar el pago:', error);
      throw error;
    }
  },

  getPaymentHistory: async (): Promise<PaymentResponse[]> => {
    try {
      const response = await api.get<PaymentResponse[]>('/payments/history');
      return response.data;
    } catch (error) {
      console.error('Error al obtener el historial:', error);
      throw error;
    }
  },

  getPaymentDetails: async (transactionId: string): Promise<PaymentResponse> => {
    try {
      const response = await api.get<PaymentResponse>(`/payments/${transactionId}`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener detalles del pago:', error);
      throw error;
    }
  }
}; 