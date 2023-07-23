import { NavLink } from 'react-router-dom'
import { NavBarContent } from '../../router/routerData'
import styles from './Navbar.module.scss'
import NavbarProfile from './NavbarProfile'
import LogoutBtn from './LogoutBtn'

const ISLOGIN = true

function Navbar() {
  return (
    <div className={styles.container}>
      <ul>
        <NavbarProfile />
        {NavBarContent.map(navElement => {
          return (
            <li key={navElement.id}>
              <NavLink to={navElement.path}>
                {({ isActive }) => (
                  <div className={isActive ? `${styles.active}` : `${styles.inactive}`}>
                    {navElement.label}
                  </div>
                )}
              </NavLink>
            </li>
          )
        })}
      </ul>
      {ISLOGIN && <LogoutBtn />}
    </div>
  )
}

export default Navbar