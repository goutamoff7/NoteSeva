import React from 'react'

const Homechatbot = () => {
  return (
    <div className='flex justify-around items-center my-[80px] ml-[30%] bg-whitee max-w-[600px] h-[200px] rounded-[40px]'>
          <div className=''>
            <img src="chatbot.png" alt="" className='h-[200px]' />
          </div>
          <div className='max-w-[300px]'>
            <h2 className=' text-xl font-bold text-center'> <span className='text-btngreen'>NoteBuddy</span> - Your AI Notes <span className='text-btngreen'>Buddy</span></h2>
            <p className='text-sm text-center'>Unlock Infinite Conversations: NoteBuddy, Your AI Companion!</p>
            <button className='h-[40px] w-[100px] bg-btngreen text-whitee rounded-full mt-[10px] ml-[100px]'>Continue</button>
          </div>
    </div>
  )
}

export default Homechatbot
