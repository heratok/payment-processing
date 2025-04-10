import axios from 'axios';

const API_URL = 'http://localhost:8081/api/notifications';

export type NotificationType = 'EMAIL' | 'WHATSAPP';

export interface PaymentDetails {
  amount: number;
  paymentMethod: string;
  date: string;
  transactionId: string;
  status?: string;
}

export interface NotificationRequest {
  type: NotificationType;
  recipient: string;
  subject: string;
  message?: string;
  paymentDetails: PaymentDetails;
}

const validateWhatsAppNumber = (number: string): boolean => {
  // Validar formato básico de número de WhatsApp (debe comenzar con + y tener entre 10 y 15 dígitos)
  const whatsappRegex = /^\+\d{10,15}$/;
  return whatsappRegex.test(number);
};

const formatPaymentMessage = (details: PaymentDetails): string => {
  return `🧾 Notificación de pago

Monto: $${details.amount.toFixed(2)}
Método: ${details.paymentMethod}
Fecha: ${details.date}
ID de transacción: ${details.transactionId}
Estado: ${details.status || 'Completado'}`;
};

export const notificationService = {
  sendNotification: async (request: NotificationRequest) => {
    try {
      if (request.type === 'WHATSAPP' && !validateWhatsAppNumber(request.recipient)) {
        throw new Error('Número de WhatsApp inválido. Debe incluir el código de país (ej: +573201234567)');
      }

      // Asegurarse de que el mensaje esté formateado correctamente
      const formattedRequest = {
        ...request,
        message: request.message || formatPaymentMessage(request.paymentDetails),
        paymentDetails: {
          ...request.paymentDetails,
          status: request.paymentDetails.status || 'Completado'
        }
      };

      // Si el mensaje personalizado está vacío, usar el mensaje formateado
      if (!formattedRequest.message) {
        formattedRequest.message = formatPaymentMessage(formattedRequest.paymentDetails);
      }

      const response = await axios.post(`${API_URL}/send`, formattedRequest);
      return response.data;
    } catch (error) {
      console.error('Error al enviar notificación:', error);
      throw error;
    }
  }
}; 