import React from 'react'
import { FaFacebookF , FaLinkedin , FaInstagram } from "react-icons/fa";
import { FaXTwitter } from "react-icons/fa6";


const Footer = () => {
  return (
    <div className="bg-[url('Footer.png')] bg-whitee bg-cover bg-center w-full h-[370px] bg-no-repeat space-y-[20px]">

      <div className="flex justify-evenly items-center ">
      
          {/* Image part */}
          <div>
            <img src="logoNote.svg" alt=""  className='h-[300px] w-[450px]'/>
          </div>

          {/* contact */}
          <div className='max-w-[300px] flex flex-col space-y-[40px]'>
            <p>
            Hello, we are NoteSeva. trying to make an effort to put the right materials your require to perform academically.
            </p>
            <div className='flex flex-col space-y-[20px]'>
              <a href="#">(123) 456-7890</a>
              <a href="#">ABC@gmail.com</a>
            </div>

            <div className='flex space-x-[15px] text-2xl'>
              <a href="#"><FaFacebookF /></a>
              <a href="https://www.linkedin.com/company/noteseva/about/"><FaLinkedin /></a>
              <a href="#"><FaXTwitter /></a>
              <a href="#"><FaInstagram /></a>
            </div>
          </div>

          {/* product */}
          <div className='space-y-[15px]'>
            <h2 className='font-bold text-lg text-darkblue'>Product</h2>
            <p>Classroom Notes</p>
            <p>organizers</p>
            <p>Previous Year Question</p>
            <p>Projects</p>
          </div>

          {/* Explore */}
          <div className='space-y-[15px]'>
          <h2 className='font-bold text-lg text-darkblue'>Explore</h2>
            <p>Resources</p>
            <p>Blog</p>
            <p>Documents</p>
            <p>communities</p>
          </div>

      </div>

      <hr className='h-[2px] bg-gray-100 border-none'/>

      <p className='text-center'>@NoteSeva, Sanaka Educational Trust's Group Of Institutions, All rights reserved</p>
        
    </div>
  
  )
}

export default Footer