import React from 'react';
import { useState } from 'react';
import { FcGoogle } from "react-icons/fc";
import { FaEye,FaEyeSlash ,FaGithub} from "react-icons/fa";



const SignUpPage = () => {

  const [showPassword, setShowPassword] = useState(false);  // State to toggle password visibility
  const [accountType,setAccountType] = useState('student');


  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);  // Toggles the visibility
  };

  return (
    <div className="min-h-screen bg-whitee flex items-center justify-center w-full pt-[40px]">
      <div className="w-[60%] bg-gray-800 p-10 rounded-xl shadow-lg flex flex-col md:flex-row space-x-0 md:space-x-10 space-y-10 md:space-y-0">

        {/* Form Section */}
        <div className="w-full md:w-1/2">
            
          <h2 className="text-3xl text-white font-bold mb-6 text-center">Create Your Free Account</h2>

          <form className="space-y-4">
          <div>
              <label className="block text-gray-400 mb-2" htmlFor="email"> Full Name</label>
              <input
                className="w-full p-3 rounded-lg bg-gray-700 text-white outline-none focus:ring-2 focus:ring-green-500"
                type="text"
                id="name"
                placeholder="John Doe"
                required
              />
            </div>

            {/* <div>
              <label className="block text-gray-400 mb-2" htmlFor="email"> University Roll No.</label>
              <input
                className="w-full p-3 rounded-lg bg-gray-700 text-white outline-none focus:ring-2 focus:ring-green-500"
                type="text"
                id="roll"
                placeholder="2780012029"
                required
              />
            </div> */}


            <div>
              <label className="block text-gray-400 mb-2" htmlFor="email">Email</label>
              <input
                className="w-full p-3 rounded-lg bg-gray-700 text-white outline-none focus:ring-2 focus:ring-green-500"
                type="email"
                id="email"
                placeholder="example@gmail.com"
                required
              />
            </div>

            <div className='relative w-full'>
              <label className="block text-gray-400 mb-2" htmlFor="password">Password</label>

              {/* Password Input Field */}
              <input
                className="w-full p-3 rounded-lg bg-gray-700 text-white outline-none focus:ring-2 focus:ring-green-500"
                type={showPassword ? "text" : "password"}  // Toggles between "text" and "password"
                id="password"
                placeholder="********"
                required
              />

              {/* Eye Icon for toggling password visibility */}
              <div className="absolute inset-y-0 top-8 right-4 flex items-center">
                {showPassword ? (
                  <FaEyeSlash
                    className="text-gray-400 cursor-pointer"
                    onClick={togglePasswordVisibility}  // Switches to hidden password
                  />
                ) : (
                  <FaEye
                    className="text-gray-400 cursor-pointer"
                    onClick={togglePasswordVisibility}  // Switches to visible password
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
                <label htmlFor="remember-me" className="text-gray-400 ml-2 cursor-pointer">Remember me</label>
              </div>
              {/* <a href="#" className="text-gray-400 hover:text-white">Admin</a> */}
            </div>

            <button
              type="submit"
              className="w-full bg-btngreen text-white p-3 rounded-lg hover:bg-green-600 transition-colors"
            >
              Create Account
            </button>
          </form>

          <div className='flex justify-center'>
            <button className="mt-4 w-full flex justify-center items-center gap-2 bg-darkblack text-white p-3 rounded-lg hover:bg-gray-600 transition-colors">
              <FcGoogle className='w-[30px] h-[30px]' />
              Google
            </button> 
            {/* <button className="mt-4 w-[45%] flex justify-center items-center gap-2 bg-darkblack text-white p-3 rounded-lg hover:bg-gray-600 transition-colors">
              <FaGithub className='w-[30px] h-[30px]' />
              GitHub
            </button>  */}

          </div>


          <p className="mt-6 text-gray-400">
            Already have an account? 
            <a href="./login" className="text-green-500 hover:text-green-600"> Login</a>
          </p>
        </div>

        {/* Illustration Section */}
        <div className="hidden md:block w-full md:w-1/2">
          <img
            src="login.png" 
            alt="Login Illustration"
            className="w-[350px] h-[350px] object-cover mt-[40%]"
          />
        </div>
      </div>
    </div>
  );
};

export default SignUpPage;
