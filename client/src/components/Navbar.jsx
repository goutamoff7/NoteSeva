import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useUser } from "../api/authApi";

const Navbar = () => {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(true);


  return (
    <div className="sticky top-0 max-h-20 flex justify-between items-center h-[100px] shadow-md bg-navcol z-10">
      {/* Logo Section */}
      <NavLink to="/">
        <img src="logoNote.svg" alt="Logo" className="h-[150px] w-[205px]" />
      </NavLink>

      {/* Navigation Links */}
      <div className="md:flex gap-12 hidden text-xl font-semibold">
        <NavLink
          to="/"
          className={({ isActive }) =>
            isActive ? "active" : "hover:underline"
          }
        >
          Home
        </NavLink>
        <NavLink
          to="/features"
          className={({ isActive }) =>
            isActive ? "active" : "hover:underline"
          }
        >
          Features
        </NavLink>
        <NavLink
          to="/project"
          className={({ isActive }) =>
            isActive ? "active" : "hover:underline"
          }
        >
          Projects
        </NavLink>
        <NavLink
          to="/contribute"
          className={({ isActive }) =>
            isActive ? "active" : "hover:underline"
          }
        >
          Contribute
        </NavLink>
        <NavLink
          to="/contactUs"
          className={({ isActive }) =>
            isActive ? "active" : "hover:underline"
          }
        >
          Contact Us
        </NavLink>
      </div>

      <div className="flex space-x-6 mr-10">
        {/* Conditional Button for Authentication */}
        {!isLoggedIn ? (
          <button
            onClick={() => navigate("/signup")}
            className="w-fit h-[40px] py-1 px-2 bg-btngreen text-white text-xl font-semibold rounded-md"
          >
            Sign up
          </button>
        ) : (
          <div className="flex items-center gap-2 cursor-pointer group relative">
            <img
              className="w-4  rounded-full"
              src="./dropdown_icon.svg"
              alt="Dropdown Icon"
            />
            <img
              className="w-10 h-10 rounded-full"
              src="./upload_area.png"
              alt="User Profile"
            />
            <div className="absolute top-0 right-0 pt-14 text-base font-medium text-gray-600 z-20 hidden group-hover:block">
              <div className="min-w-48 bg-stone-100 rounded flex flex-col gap-4 p-4">
                <p
                  onClick={() => navigate("/my-profile")}
                  className="hover:text-black cursor-pointer"
                >
                  My Profile
                </p>
                <p
                  onClick={() => navigate("/dashboard")}
                  className="hover:text-black cursor-pointer"
                >
                  Dashboard
                </p>
                <p
                  onClick={() => navigate("/bookmarked")}
                  className="hover:text-black cursor-pointer"
                >
                  Bookmarked
                </p>
                <p
                  onClick={() => navigate("/change-password")}
                  className="hover:text-black cursor-pointer"
                >
                  Change Password
                </p>
                <p
                  onClick={() => setIsLoggedIn(false) && navigate("/signup")}
                  className="hover:text-black cursor-pointer"
                >
                  Logout
                </p>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Navbar;
