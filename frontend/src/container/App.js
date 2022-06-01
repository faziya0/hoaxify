import React from 'react';
import {useSelector} from 'react-redux'
import UserLoginPage from '../pages/UserLoginPage';
import UserSignUpPage from '../pages/UserSignUpPage';
import {HashRouter as Router,Route,Redirect,Switch} from 'react-router-dom'
import HomePage from '../pages/HomePage';
import UserPage from '../pages/UserPage';
import TopBar from '../components/TopBar';
import LanguageSelector from '../components/LanguageSelector'



const App=()=> {
 const{isLoggedIn}= useSelector((store)=>{
    return{
      isLoggedIn:store.isLoggedIn
    }
  })

  return (
    <div  >
<Router>
<TopBar/>
  <Switch>
<Route exact path="/" component={HomePage}/>
{!isLoggedIn && <Route path="/login" component={UserLoginPage}/> }
<Route path="/signup" component={UserSignUpPage}/>
<Route path="/user/:username" component={UserPage}/>
<Redirect to="/"/>
</Switch>
</Router>
<LanguageSelector/>
    </div>
  );

}



export default App;
