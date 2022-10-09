import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  // 在点击浏览器的“前进/后退”，或者切换导航的时候触发。
  scrollBehavior(to, from, savePosition) {
    if (savePosition) {
      return savePosition;
    } else {
      let top;
      if (window.innerWidth >= 700) {
        top = 676
      } else {
        top = 267
      }
      return {
        x: 0,
        y: top
      }
    }
  },
  routes: [
    //首页
    {
      path: '/',
      redirect: {
        path: '/Home'
      },
      meta: {
        auth: true
      },
      name: 'Home'
    },
    {
      path: '/Home',
      component: resolve => require(['../pages/Home.vue'], resolve),
      meta: {
        auth: true
      },
      name: 'Home'
    },
    //分类
    {
      path: '/Share',
      component: resolve => require(['../pages/Share.vue'], resolve),
      meta: {
        auth: true
      },
      name: 'Share'
    },
    //分享详情
    {
      path: '/DetailArticle',
      component: resolve => require(['../pages/DetailArticle.vue'], resolve),
      meta: {
        auth: true
      },
      name: 'DetailArticle'
    },
    //赞赏
    {
      path: '/Reward',
      component: resolve => require(['../pages/Reward.vue'], resolve),
      meta: {
        auth: true
      },
      name: 'Reward'
    },
    //友链
    {
      path: '/FriendsLink',
      component: resolve => require(['../pages/FriendsLink.vue'], resolve),
      meta: {
        auth: true
      },
      name: 'FriendsLink'
    },
    //注册登录
    {
      path: '/Login',
      component: resolve => require(['../pages/Login.vue'], resolve),
      meta: {
        auth: false
      },
      name: 'Login'
    },
    //用户个人中心
    {
      path: '/UserInfo',
      component: resolve => require(['../pages/UserInfo.vue'], resolve),
      meta: {
        auth: true
      },
      name: 'UserInfo'
    }
  ]
})
