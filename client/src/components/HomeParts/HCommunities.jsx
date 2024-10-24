import React from 'react'
import { FaWhatsapp  , FaDiscord , FaTelegram} from "react-icons/fa";


const HCommunities = () => {
  return (
    <div className='fixed text-whitee text-[40px] flex flex-col space-y-[40px] right-4 mt-[10px] z-[100]'>
      <a href="#"><FaWhatsapp  className='hover:text-[#25D366]' /></a>
      <a href="#"><FaDiscord  className='hover:text-[#7289da]' /></a>
      <a href="#"><FaTelegram  className='hover:text-[#29A9EC]' /></a>
    </div>
  )
}

export default HCommunities
