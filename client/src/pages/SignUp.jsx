import React, { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { toast } from "react-toastify";

const SignUpPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [isEmailVerified, setIsEmailVerified] = useState(false);
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [otp, setOtp] = useState("");

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleVerifyEmail = () => {
    setShowOtpInput(true);
  };

  const handleVerifyOtp = () => {
    setIsEmailVerified(true);
    setShowOtpInput(false);
  };

  const handleGoogleSignUp = async () => {
    try {
      // Using VITE_BACKEND_URL from environment variables
      const backendUrl = import.meta.env.VITE_BACKEND_URL;
      
      // Make POST request to OAuth2 endpoint
      const response = await fetch(`${backendUrl}/oauth2`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        // You might need to add credentials or other data depending on your backend requirements
        credentials: 'include', // if you need to include cookies
      });

      if (!response.ok) {
        throw new Error('Google authentication failed');
      }

      const data = await response.json();
      // Handle successful Google authentication here
      toast.success('Successfully authenticated with Google');
      // You might want to redirect or update state based on the response
      
    } catch (error) {
      console.error('Google Sign Up Error:', error);
      toast.error('Failed to authenticate with Google');
    }
  };

  return (
    <div className="min-h-screen bg-darkbg flex items-center justify-center w-full">
      <div className="min-w-[35%] w-fit bg-[#475569] border-gray-200 p-10 rounded-xl shadow-lg flex flex-col space-x-0 md:space-x-10 space-y-10 md:space-y-0">
        <div className="w-full">
          <h2 className="text-3xl text-white font-bold mb-6 text-center">
            Create Your Free Account
          </h2>

          <form className="space-y-4">
            <div>
              <label className="block text-whitee mb-2" htmlFor="name">
                Full Name
              </label>
              <input
                className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
                type="text"
                id="name"
                placeholder="John Doe"
                required
              />
            </div>

            <div className="flex items-end gap-2">
              <div className="flex-1">
                <label className="block text-whitee mb-2" htmlFor="email">
                  Email
                </label>
                <input
                  className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
                  type="email"
                  id="email"
                  placeholder="example@gmail.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  disabled={isEmailVerified}
                />
              </div>
              {!isEmailVerified && !showOtpInput && (
                <button
                  type="button"
                  onClick={handleVerifyEmail}
                  className="bg-btngreen text-white p-3 rounded-lg hover:bg-green-600 transition-colors"
                  disabled={!email}
                >
                  Verify Email
                </button>
              )}
            </div>

            {showOtpInput && (
              <div className="flex items-end gap-2">
                <div className="flex-1">
                  <label className="block text-whitee mb-2" htmlFor="otp">
                    Enter OTP
                  </label>
                  <input
                    className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
                    type="text"
                    id="otp"
                    placeholder="Enter OTP"
                    value={otp}
                    onChange={(e) => setOtp(e.target.value)}
                    required
                  />
                </div>
                <button
                  type="button"
                  onClick={handleVerifyOtp}
                  className="bg-btngreen text-white p-3 rounded-lg hover:bg-green-600 transition-colors"
                  disabled={!otp}
                >
                  Submit OTP
                </button>
              </div>
            )}

            {isEmailVerified && (
              <div className="relative w-full">
                <label className="block text-whitee mb-2" htmlFor="password">
                  Password
                </label>
                <input
                  className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
                  type={showPassword ? "text" : "password"}
                  id="password"
                  placeholder="********"
                  required
                />
                <div className="absolute inset-y-0 top-8 right-4 flex items-center">
                  {showPassword ? (
                    <FaEyeSlash
                      className="text-gray-400 cursor-pointer"
                      onClick={togglePasswordVisibility}
                    />
                  ) : (
                    <FaEye
                      className="text-gray-400 cursor-pointer"
                      onClick={togglePasswordVisibility}
                    />
                  )}
                </div>
              </div>
            )}

            {isEmailVerified && (
              <>
                <div className="flex justify-between items-center">
                  <div className="flex items-center">
                    <input
                      type="checkbox"
                      id="remember-me"
                      className="text-green-500 focus:ring-green-500 cursor-pointer"
                    />
                    <label
                      htmlFor="remember-me"
                      className="text-whitee ml-2 cursor-pointer"
                    >
                      Remember me
                    </label>
                  </div>
                </div>

                <button
                  type="submit"
                  className="w-full bg-btngreen text-white p-3 rounded-lg hover:bg-green-600 transition-colors"
                >
                  Create Account
                </button>
              </>
            )}
          </form>

          <div className="flex justify-center">
            <button 
              onClick={handleGoogleSignUp}
              className="mt-4 w-full flex justify-center items-center gap-2 bg-darkblack text-white p-3 rounded-lg hover:bg-gray-800 transition-colors"
            >
              <FcGoogle className="w-[30px] h-[30px]" />
              Google
            </button>
          </div>

          <p className="mt-6 text-gray-400">
            Already have an account?{" "}
            <a href="./login" className="text-green-500 hover:text-green-600">
              Login
            </a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default SignUpPage;