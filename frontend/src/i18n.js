import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import {register} from 'timeago.js'
i18n.use(initReactI18next).init({
    resources:{
        en:{
            translations:{
            'Sign Up':"Sign Up",
            'password mismatch':'password mismatch',
            'Username':'Username',
            'Display Name':'Display Name',
            'Password':'Password',
            'Password Repeat':'Password Repeat',
            'Sign up':'Sign up',
            'Login':'Login',
            'Log out':'Log out',
            'Users':'Users',
            'Previous':'<previous',
            'Next':'next>',
            'User can not found!':'User can not found!',
            'Size of displayname must be between 4 and 200':'Size of displayname must be between 4 and 200',
            'Edit':'Edit',
            'Save':'Save',
            'Cancel':'Cancel',
            'change display name':'change display name',
           'There is no hoax': 'There is no hoax',
           'Load new hoaxes':'Load new hoaxes',
           'Load old hoaxes':'Load old hoaxes',
           'My Profile':'My Profile',
           'Delete My Account':'Delete My Account',
           'Hoax has unknown attachment':'Hoax has unknown attachment',
           'Are you sure to delete hoax?':'Are you sure to delete hoax?',
           'Delete Hoax':'Delete Hoax',
           'Are you sure delete your account?':'Are you sure delete your account?',
           'Delete my account':'Delete my account'


        

        
            
            


            }
        },
        az:{
            translations:{
                'Sign Up':' Qeydiyyatdan keç',
                'password mismatch':'parol uyğunsuzluğu',
                'Username':'İstifadəçi adı',
                'Display Name':'Display Name',
                'Password':'Parol',
                'Password Repeat':'Parol Təkrarı',
                'Sign up':'Qeydiyyatdan keç',
                'Login':'Daxil Ol',
                'Log out':'Çıxış',
                'Users':'İstifadəçilər',
                'Previous':'<əvvəlki',
                'Next':'növbəti>',
                'User can not found!':'İstifadəçi tapılmadı!',
                'Size of displayname must be between 4 and 200':'Displayname minimum 4 maksimum 200 simvoldan ibarət olmalıdır',
                'Edit':'düzəliş et',
                'Save':'Qeyd et',
                'Cancel':'Ləğv et',
                'change display name':'display name dəyişdir',
                'There is no hoax': 'Hoax yoxdur',
                'Load new hoaxes':'Yeni hoaxları yüklə',
                'Load old hoaxes':'Əvvəlki hoaxları yüklə',
                'My Profile':'Profilim',
                'Delete My Account':'Hesabımı sil',
                'Hoax has unknown attachment':'Hoaxun bilinməyən əlavəsi var',
                'Are you sure to delete hoax?':'hoaxu silmək istədiyinizə əminsiniz?',
                'Delete Hoax':'Hoaxu sil',
                'Are you sure delete your account?':'Hesabınızı silmək istədiyinizə əminsiniz?',
                'Delete my account':'Hesabımı sil'




            }
        }

    },
    fallbackLng:'en',
    ns:['translations'],
    defaultNS:'translations',
    keySeparator:false,
    interpolation:{
        escapeValue:false,
        formatSeparator:','
    },
    react:{
        wait:true
    }

})

const timeageAz=(number, index)=>{
    return [
      ['indi', 'şimdi'],
      ['%s saniyə əvvəl', '%s saniyə əvvəl'],
      ['1 dəqiqə əvvəl', '1 dəqiqə əvvəl'],
      ['%s dəqiqə əvvəl', '%s dəqiqə əvvəl'],
      ['1 saat əvvəl', '1 saat əvvəl'],
      ['%s saat əvvəl', '%s saat əvvəl'],
      ['1 gün əvvəl', '1 gün əvvəl'],
      ['%s gün əvvəl', '%s gün əvvəl'],
      ['1 həftə əvvəl', '1 həftə əvvəl'],
      ['%s həftə əvvəl', '%s həftə əvvəl'],
      ['1 ay əvvəl', '1 ay əvvəl'],
      ['%s ay əvvəl', '%s ay əvvəl'],
      ['1 il əvvəl', '1 il əvvəl'],
      ['%s il əvvəl', '%s il əvvəl'],
    ][index] ;
  }
  register('az',timeageAz)

export default i18n;