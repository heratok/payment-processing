import axios from 'axios';

const API_URL = 'http://localhost:8082/api';

export interface PaymentData {
  amount: number;
  paymentMethod: string;
  date: string;
  transactionId: string;
  status: string;
}

export interface ReportOptions {
  includeLogo: boolean;
  title: string;
  includePaymentDetails: boolean;
  includeUserInfo: boolean;
  theme: 'LIGHT' | 'DARK';
  includeTimestamp: boolean;
  footerMessage: string;
  format: 'A4' | 'LETTER';
  paymentData: PaymentData;
}

export const reportService = {
  generateReport: async (options: ReportOptions): Promise<Blob> => {
    try {
      const response = await axios.post(`${API_URL}/reports/generate`, options, {
        responseType: 'blob'
      });
      return response.data;
    } catch (error) {
      console.error('Error al generar el reporte:', error);
      throw error;
    }
  },

  generateReportFromTemplate: async (customOptions: Partial<ReportOptions>): Promise<Blob> => {
    try {
      const response = await axios.post(`${API_URL}/reports/generate-from-template`, customOptions, {
        responseType: 'blob'
      });
      return response.data;
    } catch (error) {
      console.error('Error al generar el reporte desde plantilla:', error);
      throw error;
    }
  }
}; 