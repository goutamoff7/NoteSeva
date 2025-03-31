import { useState, useEffect } from "react";
import axios from "axios";
import { FiEye, FiEyeOff } from "react-icons/fi";
import { toast } from "react-toastify";
import { useAppContext } from "../context/AppContext";
import { useNavigate } from "react-router-dom";

const ForgotPassword = () => {
  const [step, setStep] = useState(1);
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [isConfirmPasswordVisible, setIsConfirmPasswordVisible] = useState(false);
  const [timer, setTimer] = useState(0);

  const navigate = useNavigate();
  const { backendUrl } = useAppContext();

  useEffect(() => {
    let interval;
    if (timer > 0) {
      interval = setInterval(() => {
        setTimer((prev) => prev - 1);
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [timer]);

  // Send OTP API Call
  const handleSendOtp = async () => {
    try {
      const response = await axios.post(`${backendUrl}/public/generate-otp`, { email });
      toast.success(response.data.message || "OTP Sent Successfully");
      setStep(2);
      setTimer(60); // Start 1-minute timer
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to send OTP");
    }
  };

  // Verify OTP API Call
  const handleVerifyOtp = async () => {
    try {
      const response = await axios.post(`${backendUrl}/public/verify-otp`, { email, otp });
      toast.success(response.data.message || "OTP Verified Successfully");
      setStep(3);
    } catch (error) {
      toast.error(error.response?.data?.message || "Invalid OTP");
    }
  };

  // Reset Password API Call
  const handleResetPassword = async () => {
    try {
      const response = await axios.put(`${backendUrl}/public/forgot-password`, {
        email,
        newPassword,
        confirmPassword,
      });
      toast.success(response.data.message || "Password Changed Successfully");
      setStep(1);
      setEmail("");
      setNewPassword("");
      setConfirmPassword("");
      navigate("/login");
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to reset password");
    }
  };

  return (
    <div className="bg-darkbg w-full h-screen flex justify-center items-center">
      <div className="max-w-md min-w-[30%] shadow-xl border bg-[#475569] rounded-2xl overflow-hidden p-8">
        <h2 className="text-3xl font-bold mb-6 text-center text-btngreen">Forgot Password</h2>

        {step === 1 && (
          <>
            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full p-3 bg-gray-800 text-white rounded-lg focus:ring-2 focus:ring-green-500 mb-4"
            />
            <button onClick={handleSendOtp} className="w-full bg-btngreen text-white p-3 rounded-lg hover:bg-green-600">
              Send OTP
            </button>
          </>
        )}

        {step === 2 && (
          <>
            <input
              type="text"
              placeholder="Enter OTP"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              className="w-full p-3 bg-gray-800 text-white rounded-lg focus:ring-2 focus:ring-green-500 mb-4"
            />
            <button onClick={handleVerifyOtp} className="w-full bg-btngreen text-white p-3 rounded-lg hover:bg-green-600">
              Verify OTP
            </button>
            <div className="text-center mt-4">
              {timer > 0 ? (
                <p className="text-gray-400">Resend OTP in {timer}s</p>
              ) : (
                <button onClick={handleSendOtp} className="text-btngreen font-semibold hover:underline">
                  Resend OTP
                </button>
              )}
            </div>
          </>
        )}

        {step === 3 && (
          <>
            <div className="relative mb-4">
              <input
                type={isPasswordVisible ? "text" : "password"}
                placeholder="New Password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                className="w-full p-3 bg-gray-800 text-white rounded-lg focus:ring-2 focus:ring-green-500 pr-12"
              />
              <button
                type="button"
                onClick={() => setIsPasswordVisible(!isPasswordVisible)}
                className="absolute inset-y-0 right-4 flex items-center text-gray-500"
              >
                {isPasswordVisible ? <FiEyeOff size={20} /> : <FiEye size={20} />}
              </button>
            </div>

            <div className="relative mb-4">
              <input
                type={isConfirmPasswordVisible ? "text" : "password"}
                placeholder="Confirm New Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className="w-full p-3 bg-gray-800 text-white rounded-lg focus:ring-2 focus:ring-green-500 pr-12"
              />
              <button
                type="button"
                onClick={() => setIsConfirmPasswordVisible(!isConfirmPasswordVisible)}
                className="absolute inset-y-0 right-4 flex items-center text-gray-500"
              >
                {isConfirmPasswordVisible ? <FiEyeOff size={20} /> : <FiEye size={20} />}
              </button>
            </div>

            <button onClick={handleResetPassword} className="w-full bg-btngreen text-white p-3 rounded-lg hover:bg-green-600">
              Create New Password
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default ForgotPassword;
