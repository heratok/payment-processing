import React, { useState } from "react";
import { paymentService, PaymentRequest } from "../services/api";
import { motion } from "framer-motion";
import { useTheme } from "../theme/ThemeContext";
import { CreditCard, Wallet } from "lucide-react";
import { InvoiceModal } from './InvoiceModal';

interface PaymentMethodButtonProps {
  selected: boolean;
  onClick: () => void;
  icon: React.ReactNode;
  label: string;
}

const PaymentMethodButton: React.FC<PaymentMethodButtonProps> = ({
  selected,
  onClick,
  icon,
  label,
}) => {
  return (
    <motion.button
      type="button" // <- esto evita que el botón envíe el formulario
      whileHover={{ scale: 1.02 }}
      whileTap={{ scale: 0.98 }}
      onClick={onClick}
      className={`flex items-center justify-center gap-2 px-6 py-4 rounded-lg transition-all duration-300 ${
        selected
          ? "bg-primary text-primary-foreground shadow-lg"
          : "bg-card hover:bg-accent text-foreground hover:text-accent-foreground border border-border"
      }`}
    >
      {icon}
      <span className="font-medium">{label}</span>
    </motion.button>
  );
};

// Componente personalizado para el ícono de PayPal ya que no existe en lucide-react
const PayPalIcon = () => (
  <svg role="img" viewBox="0 0 24 24" className="w-5 h-5" fill="none">
    <path
      d="M7.076 21.337H2.47a.641.641 0 0 1-.633-.74L4.944.901C5.026.382 5.474 0 5.998 0h7.46c2.57 0 4.578.543 5.69 1.81 1.01 1.15 1.304 2.42 1.012 4.287-.023.143-.047.288-.077.437-.983 5.05-4.349 6.797-8.647 6.797h-2.19c-.524 0-.968.382-1.05.9l-1.12 7.106zm14.146-14.42a3.35 3.35 0 0 0-.607-.541c-.013.076-.026.175-.041.254-.93 4.778-4.005 7.201-9.138 7.201h-2.19a.563.563 0 0 0-.556.479l-1.187 7.527h-.506l-.24 1.516a.56.56 0 0 0 .554.647h3.882c.46 0 .85-.334.922-.788.06-.26.76-4.852.816-5.09a.932.932 0 0 1 .923-.788h.58c3.76 0 6.705-1.528 7.565-5.946.36-1.847.174-3.388-.777-4.471z"
      fill="currentColor"
    />
  </svg>
);

export const PaymentForm: React.FC = () => {
  const [formData, setFormData] = useState<PaymentRequest>({
    amount: 0,
    paymentMethod: "creditcard",
  });
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<{
    success: boolean;
    message: string;
  } | null>(null);
  const [showInvoice, setShowInvoice] = useState(false);
  const [lastPaymentAmount, setLastPaymentAmount] = useState(0);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === "amount" ? (value === "" ? 0 : Number(value)) : value,
    }));
  };

  const handlePaymentMethodSelect = (method: string) => {
    setFormData((prev) => ({
      ...prev,
      paymentMethod: method,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log("🔔 Formulario enviado. Procesando pago con:", formData); // <- Esta línea es nueva
    setLoading(true);
    setResult(null);

    try {
      const response = await paymentService.processPayment(formData);
      setResult({
        success: response.success,
        message: response.message,
      });
      if (response.success) {
        setLastPaymentAmount(formData.amount);
        setShowInvoice(true);
        setFormData({
          amount: 0,
          paymentMethod: "creditcard",
        });
      }
    } catch (error) {
      setResult({
        success: false,
        message: "Error al procesar el pago",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="max-w-2xl mx-auto space-y-8"
    >
      <h2 className="text-2xl font-bold text-foreground">Nuevo Pago</h2>

      <form onSubmit={handleSubmit} className="space-y-8">
        <div className="space-y-4">
          <label
            htmlFor="amount"
            className="block text-sm font-medium text-foreground"
          >
            Monto
          </label>
          <div className="relative">
            <input
              type="number"
              id="amount"
              name="amount"
              value={formData.amount || ""}
              onChange={handleChange}
              className="w-full px-4 py-3 text-lg border rounded-lg bg-card text-foreground border-input focus:ring-2 focus:ring-ring focus:border-ring placeholder:text-muted-foreground"
              placeholder="Ingrese el monto"
              required
              min="0"
              step="0.01"
            />
          </div>
        </div>

        <div className="space-y-4">
          <label className="block text-sm font-medium text-foreground">
            Método de Pago
          </label>
          <div className="grid grid-cols-1 gap-3 sm:grid-cols-3">
            <PaymentMethodButton
              selected={formData.paymentMethod === "creditcard"}
              onClick={() => handlePaymentMethodSelect("creditcard")}
              icon={<CreditCard className="w-5 h-5" />}
              label="Tarjeta de Crédito"
            />
            <PaymentMethodButton
              selected={formData.paymentMethod === "debitcard"}
              onClick={() => handlePaymentMethodSelect("debitcard")}
              icon={<Wallet className="w-5 h-5" />}
              label="Tarjeta de Débito"
            />
            <PaymentMethodButton
              selected={formData.paymentMethod === "paypal"}
              onClick={() => handlePaymentMethodSelect("paypal")}
              icon={<PayPalIcon />}
              label="PayPal"
            />
          </div>
        </div>

        <div className="flex justify-center mt-8">
          <motion.button
            whileHover={{ scale: 1.01 }}
            whileTap={{ scale: 0.99 }}
            type="submit"
            disabled={loading}
            className={`flex items-center justify-center w-full py-4 px-8 rounded-xl font-semibold text-lg transition-all duration-300 ${
              loading
                ? "bg-muted text-muted-foreground cursor-not-allowed"
                : "bg-primary text-primary-foreground hover:opacity-90 shadow-lg"
            }`}
          >
            {loading ? (
              <div className="flex items-center justify-center gap-3">
                <div className="w-5 h-5 border-b-2 border-current rounded-full animate-spin"></div>
                <span>Procesando...</span>
              </div>
            ) : (
              "Procesar Pago"
            )}
          </motion.button>
        </div>
      </form>

      {result && (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className={`p-4 rounded-lg border ${
            result.success
              ? "bg-green-500/10 text-green-500 border-green-500/20"
              : "bg-destructive/10 text-destructive border-destructive/20"
          }`}
        >
          <p className="font-medium text-center">{result.message}</p>
        </motion.div>
      )}

      <InvoiceModal
        isOpen={showInvoice}
        onClose={() => setShowInvoice(false)}
        paymentData={{
          amount: lastPaymentAmount,
          paymentMethod: formData.paymentMethod,
          date: new Date().toLocaleDateString(),
          transactionId: Math.random().toString(36).substring(2, 10).toUpperCase(),
          status: result?.success ? 'Completado' : 'Fallido'
        }}
      />
    </motion.div>
  );
};
