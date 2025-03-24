import React, { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaEye, FaEyeSlash, FaGithub } from "react-icons/fa";
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
    // Here you would typically send an OTP to the email
    // For this example, we'll just show the OTP input
      setShowOtpInput(true);
      
  };

  const handleVerifyOtp = () => {
    // Here you would typically verify the OTP with your backend
    // For this example, we'll assume any OTP is valid
    setIsEmailVerified(true);
    setShowOtpInput(false);
  };

  return (
    <div className="min-h-screen bg-darkbg flex items-center justify-center w-full">
      <div className="min-w-[35%] w-fit bg-[#475569] border-gray-200 p-10 rounded-xl shadow-lg flex flex-col space-x-0 md:space-x-10 space-y-10 md:space-y-0">
        {/* Form Section */}
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
                  disabled={isEmailVerified} // Disable email input after verification
                />
              </div>
              {!isEmailVerified && !showOtpInput && (
                <button
                  type="button"
                  onClick={handleVerifyEmail}
                  className="bg-btngreen text-white p-3 rounded-lg hover:bg-green-600 transition-colors"
                  disabled={!email} // Disable if email is empty
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
                  disabled={!otp} // Disable if OTP is empty
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
            <button className="mt-4 w-full flex justify-center items-center gap-2 bg-darkblack text-white p-3 rounded-lg">
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