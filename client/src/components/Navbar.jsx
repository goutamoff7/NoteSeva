import React from 'react';
import { NavLink } from 'react-router-dom';

const Navbar = () => {

  // Navitems array
  const navitems = [
    {path:"/",link:"Home"},
    {path:"/course",link:"Courses"},
    {path:"/project",link:"Projects"},
    {path:"/contribute",link:"Contribute"},
    {path:"/contact",link:"Contact Us"},
  ];

  return (
    <div className='flex justify-between items-center h-[100px] shadow-md bg-navcol'>
      
      {/* Logo Section */}
      <NavLink to="/"><img src="logoNote.svg" alt="Logo" className='h-[150px] w-[205px]'/></NavLink>

      {/* Navigation Links */}
      <div className='md:flex gap-12 text-lg hidden'>
        {
          navitems.map(({ path, link }) => (
            <li className='text-darkblack font-semibold text-2xl list-none' key={path}>
              <NavLink 
                to={path} 
                className={({ isActive, isPending }) =>
                  isActive
                    ? "active" // Active state
                    : isPending
                    ? "pending" // Pending state (optional)
                    : "hover:underline" // Default hover state
                }
              >
                {link}
              </NavLink>
            </li>
          ))
        }
      </div>

      <div className='flex space-x-6 mr-10'>

        <NavLink to="/login">
          <button className='w-[80px] h-[40px] bg-btngreen text-white text-xl text-semibold rounded-sm'>
            Login
          </button>
        </NavLink>

        <NavLink to="/signup">
          <button className='w-[100px] h-[40px] bg-btngreen text-white text-xl text-semibold rounded-sm'>
            Sign Up
          </button>
        </NavLink>

        <NavLink to="/logout">
          <button className='hidden w-[100px] h-[40px] bg-btngreen text-white text-xl text-semibold rounded-sm'>
            Log Out
          </button>
        </NavLink>

        <NavLink to="/myprofile">
          <button className='hidden w-[100px] h-[40px] bg-btngreen text-white text-xl text-semibold rounded-sm'>
            My Profile
          </button>
        </NavLink>
      </div>
      
    </div>
  );
};

export default Navbar;
