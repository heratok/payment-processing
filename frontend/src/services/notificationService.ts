import axios from 'axios';

const API_URL = 'http://localhost:8081/api/notifications';

export interface PaymentDetails {
  amount: number;
  paymentMethod: string;
  date: string;
  transactionId: string;
}

export interface NotificationRequest {
  type: string;
  recipient: string;
  subject: string;
  message?: string;
  paymentDetails: PaymentDetails;
}

export const notificationService = {
  sendNotification: async (request: NotificationRequest) => {
    try {
      const response = await axios.post(`${API_URL}/send`, request);
      return response.data;
    } catch (error) {
      console.error('Error al enviar notificación:', error);
      throw error;
    }
  }
}; 