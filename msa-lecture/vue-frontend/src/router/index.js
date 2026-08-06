import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/store/auth.js'

/**
 * Vue Router 경로(Route) 정의
 * - meta 속성을 활용한 접근 권한 세부 제어:
 *   - requiresAuth: 로그인 인증 필요
 *   - guestOnly: 비로그인(게스트) 사용자만 접근 가능
 *   - instructorOnly: 강사(INSTRUCTOR) 권한 계정만 접근 가능
 */
const routes = [
  {
    path: '/',
    name: 'Landing',
    component: () => import('@/views/LandingView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginView.vue'),
    meta: { guestOnly: true }
  },
  {
    path: '/callback',
    name: 'Callback',
    component: () => import('@/views/CallbackView.vue')
  },
  {
    path: '/courses',
    name: 'CourseList',
    component: () => import('@/views/CourseListView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/courses/new',
    name: 'CourseCreate',
    component: () => import('@/views/CourseCreateView.vue'),
    meta: { requiresAuth: true, instructorOnly: true }
  },
  {
    path: '/courses/:id(\\d+)',
    name: 'CourseDetail',
    component: () => import('@/views/CourseDetailView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/enrollments',
    name: 'Enrollment',
    component: () => import('@/views/EnrollmentView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: () => import('@/views/MyPageView.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

/**
 * 전역 네비게이션 가드 (Navigation Guard)
 * - 페이지 이동 시 인증 여부 및 권한 검증 수행
 */
router.beforeEach((to) => {
  const auth = useAuthStore()

  // 1. 인증이 필요한 페이지 접근 시 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return { name: 'Login' }
  }

  // 2. 게스트 전용 페이지(로그인 등)에 이미 로그인된 사용자가 접근 시 강의 목록으로 이동
  if (to.meta.guestOnly && auth.isAuthenticated) {
    return { name: 'CourseList' }
  }

  // 3. 강사 전용 페이지에 일반 학생(STUDENT) 계정이 접근 시 강의 목록으로 이동
  if (to.meta.instructorOnly && auth.user?.role !== 'INSTRUCTOR') {
    return { name: 'CourseList' }
  }
})

export default router