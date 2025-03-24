import React from "react";
import { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaEye, FaEyeSlash, FaGithub } from "react-icons/fa";

const LoginPage = () => {
  const [showPassword, setShowPassword] = useState(false); // State to toggle password visibility

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword); // Toggles the visibility
  };

  return (
    <div className="min-h-screen bg-darkbg flex items-center justify-center w-full">

      <div className="min-w-[35%] w-fit bg-[#475569] border-gray-200 p-10 rounded-xl shadow-lg flex flex-col space-x-0 md:space-x-10 space-y-10 md:space-y-0">
      
        {/* Form Section */}
        <div className="w-full">
          <h2 className="text-3xl text-whitee font-bold mb-6 text-center">
            WELCOME BACK
          </h2>
          <p className="text-whitee text-center mb-4">
            Welcome back! Please enter your details.
          </p>

          <form className="space-y-6">
            <div>
              <label className="block text-whitee mb-2" htmlFor="email">
                Email
              </label>
              <input
                className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
                type="email"
                id="email"
                placeholder="Enter your email"
              />
            </div>

            <div className="relative w-full">
              <label className="block text-whitee mb-2" htmlFor="password">
                Password
              </label>

              {/* Password Input Field */}
              <input
                className="w-full p-3 rounded-lg bg-gray-800 text-white outline-none focus:ring-2 focus:ring-green-500"
                type={showPassword ? "text" : "password"}
                id="password"
                placeholder="********"
              />

              {/* Eye Icon for toggling password visibility */}
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
              <a href="forgot-password" className="text-whitee hover:text-btngreen">
                Forgot password
              </a>
            </div>

            <button
              type="submit"
              className="w-full bg-btngreen text-white p-3 rounded-lg hover:bg-green-600 transition-colors"
            >
              Sign in
            </button>
          </form>

          <div className="flex justify-center">
            <button className="mt-4 w-full flex justify-center items-center gap-2 bg-darkblack text-white p-3 rounded-lg">
              <FcGoogle className="w-[30px] h-[30px]" />
              Google
            </button>
          </div>

          <p className="mt-6 text-gray-400">
            Don’t have an account?
            <a href="./signup" className="text-green-500 hover:text-green-600">
              {" "}
              Sign up for free!
            </a>
          </p>
        </div>

      </div>
    </div>
  );
};

export default LoginPage;
