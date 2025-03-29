import { useState } from "react";
import axios from "axios";
import { FiEye, FiEyeOff } from "react-icons/fi";
import { toast } from "react-toastify";

const ForgotPassword = () => {
  const [step, setStep] = useState(1);
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [isConfirmPasswordVisible, setIsConfirmPasswordVisible] = useState(false);

  // Send OTP API Call
  const handleSendOtp = async () => {
    try {
      const response = await axios.post("", { email });
      toast.success(response.data.message);
      setStep(2);
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to send OTP");
    }
  };

  // Verify OTP API Call
  const handleVerifyOtp = async () => {
    try {
      const response = await axios.post("", { email, otp });
      toast.success(response.data.message);
      setStep(3);
    } catch (error) {
      toast.error(error.response?.data?.message || "Invalid OTP");
    }
  };

  // Reset Password API Call
  const handleResetPassword = async () => {
    if (password.length < 8) {
      toast.error("Password must be at least 8 characters long!");
      return;
    }
    if (password !== confirmPassword) {
      toast.error("Passwords do not match!");
      return;
    }
    try {
      const response = await axios.post("", { email, password, confirmPassword });
      toast.success(response.data.message);
      setStep(1);
      setEmail("");
      setPassword("");
      setConfirmPassword("");
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
          </>
        )}
        
        {step === 3 && (
          <>
            <div className="relative mb-4">
              <input
                type={isPasswordVisible ? "text" : "password"}
                placeholder="New Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
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
