import React from 'react'
import Footer from '../components/Footer'
import { CiSearch } from "react-icons/ci";

const Home = () => {
  return (
    <div className="bg-[url('darkbg.png')] bg-cover bg-center h-screen bg-repeat">

        {/* course search options */}
        <div className='flex items-center justify-evenly pt-[40px]'>
          {/* Left Part */}
          <div className='max-w-[400px]'>
            <h2 className='font-poppins font-bold text-3xl text-whitee text-center'>
              <span className='text-btngreen'>Studying</span> Online is now much easier
            </h2>
            <p className='text-whitee text-center mt-[20px] font-poppins text-sm'>
              Welcome to Noteseva – your ultimate academic companion! Dive into a world of knowledge with our extensive collection of meticulously crafted notes and comprehensive sets of previous year questions
            </p>
            <div className='relative mt-[40px]'>
              <input className='w-[380px] h-[50px] rounded-full pl-[40px] outline-none'
              type="text" placeholder='Search For Courses'/>
              <CiSearch  className='absolute top-4 left-4 h-[20px] w-[20px]'/>
              <button className='absolute rounded-full top-1 right-6 bg-btngreen h-[42px] w-24'>Continue</button>
            </div> 
          </div>

          {/* Right Part */}
          <div>
            <img src="group.png" alt="Group Icons" className='w-[500px] h-[303px]' />
          </div>

        </div>

        {/* Chatbots */}
        <div></div>

        {/* Our courses */}
        <div></div>

        {/* Contents */}
        <div></div>

        {/* contribute */}
        <div></div>

        {/* Footer */}
        {/* <Footer></Footer> */}

    </div>
  )
}

export default Home