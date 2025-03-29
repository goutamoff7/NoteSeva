import React, { useContext, useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { toast } from "react-toastify";
import axios from "axios";
import { useAppContext } from "../context/AppContext";
import { Navigate, useNavigate } from "react-router-dom";

const SignUpPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [isEmailVerified, setIsEmailVerified] = useState(false);
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [otp, setOtp] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const { backendUrl } = useAppContext();

  const navigate = useNavigate()

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleConfirmPasswordVisibility = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  // API for Verify Email
  const handleVerifyEmail = async () => {
    try {
      await axios.post(`${backendUrl}/public/generate-otp`, { email });
      toast.success("OTP sent to your email");
      setShowOtpInput(true);
    } catch (error) {
      toast.error("Failed to send OTP");
    }
  };

  // API for verify otp
  const handleVerifyOtp = async () => {
    try {
      await axios.post(`${backendUrl}/public/verify-otp`, { email, otp });
      toast.success("Email verified successfully");
      setIsEmailVerified(true);
      setShowOtpInput(false);
    } catch (error) {
      toast.error("Invalid OTP");
    }
  };

  // API for signup
  const handleSignUp = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      toast.error("Passwords do not match");
      return;
    }
    try {
      await axios.post(`${backendUrl}/public/register`, { name, email, password, confirmPassword });
      toast.success("Account created successfully");
      navigate('/login');
    } catch (error) {
      toast.error("Signup failed");
    }
  };

  const handleGoogleSignUp = () => {
    window.location.href = `${backendUrl}/oauth2/authorization/google`;
  };

  return (
    <div className="min-h-screen bg-darkbg flex items-center justify-center w-full">
      <div className="min-w-[35%] bg-[#475569] p-10 rounded-xl shadow-lg">
        <h2 className="text-3xl text-white font-bold mb-6 text-center">
          Create Your Free Account
        </h2>

        <form className="space-y-4" onSubmit={handleSignUp}>
          {!isEmailVerified && (
            <>
              <div className="flex items-end gap-2">
                <input
                  className="flex-1 p-3 bg-gray-800 text-white rounded-lg"
                  type="email"
                  placeholder="example@gmail.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  disabled={isEmailVerified}
                />
                {!showOtpInput && (
                  <button
                    type="button"
                    onClick={handleVerifyEmail}
                    className="bg-btngreen text-white p-3 rounded-lg"
                    disabled={!email}
                  >
                    Verify Email
                  </button>
                )}
              </div>

              {showOtpInput && (
                <div className="flex items-end gap-2">
                  <input
                    className="flex-1 p-3 bg-gray-800 text-white rounded-lg"
                    type="text"
                    placeholder="Enter OTP"
                    value={otp}
                    onChange={(e) => setOtp(e.target.value)}
                    required
                  />
                  <button
                    type="button"
                    onClick={handleVerifyOtp}
                    className="bg-btngreen text-white p-3 rounded-lg"
                    disabled={!otp}
                  >
                    Submit OTP
                  </button>
                </div>
              )}
            </>
          )}

          {isEmailVerified && (
            <>
              <div>
                <label className="block text-white mb-2">Full Name</label>
                <input
                  className="w-full p-3 rounded-lg bg-gray-800 text-white"
                  type="text"
                  placeholder="John Doe"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                />
              </div>

              <div className="relative w-full">
                <input
                  className="w-full p-3 bg-gray-800 text-white rounded-lg"
                  type={showPassword ? "text" : "password"}
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
                <div className="absolute inset-y-0 top-4 right-4">
                  {showPassword ? (
                    <FaEyeSlash
                      className="cursor-pointer text-gray-400"
                      onClick={togglePasswordVisibility}
                    />
                  ) : (
                    <FaEye
                      className="cursor-pointer text-gray-400"
                      onClick={togglePasswordVisibility}
                    />
                  )}
                </div>
              </div>

              <div className="relative w-full">
                <input
                  className="w-full p-3 bg-gray-800 text-white rounded-lg"
                  type={showConfirmPassword ? "text" : "password"}
                  placeholder="Confirm Password"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  required
                />
                <div className="absolute inset-y-0 top-4 right-4">
                  {showConfirmPassword ? (
                    <FaEyeSlash
                      className="cursor-pointer text-gray-400"
                      onClick={toggleConfirmPasswordVisibility}
                    />
                  ) : (
                    <FaEye
                      className="cursor-pointer text-gray-400"
                      onClick={toggleConfirmPasswordVisibility}
                    />
                  )}
                </div>
              </div>

              <button type="submit" className="w-full bg-btngreen text-white p-3 rounded-lg">
                Create Account
              </button>
            </>
          )}
        </form>

        <button
          onClick={handleGoogleSignUp}
          className="mt-4 w-full flex justify-center items-center gap-2 bg-darkblack text-white p-3 rounded-lg"
        >
          <FcGoogle className="w-[30px] h-[30px]" /> Google
        </button>
        <p className="mt-6 text-white">
          Already have an account?{" "}
          <a href="./login" className="text-green-500 hover:text-green-600">
            Login
          </a>
        </p>
      </div>
    </div>
  );
};

export default SignUpPage;