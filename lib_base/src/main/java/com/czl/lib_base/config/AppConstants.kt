package com.czl.lib_base.config

/**
 * @author Alwyn
 * @Date 2020/10/22
 * @Description 常量管理类
 */
interface AppConstants {
    object Common {
        const val PAGE_SIZE = 100

    }

    object BundleKey {
        const val KEY_REHAB_TYPE = "rehabType"
        const val KEY_ClASS_ID = "classId"
        const val KEY_COURSE_ID = "courseId"
        const val KEY_MEDIA_ID = "mediaId"
        const val KEY_MEDIA_TYPE = "mediaType"


    }

    object SpKey {
        const val USER_TOKEN: String = "user_token"
        const val USER_JSON_DATA: String = "user_json_data"
    }

    /**
     * value规则： /(module后缀)/(所在类名)
     * 路由 A_ : Activity
     *     F_ : Fragment
     */
    interface Router {

        object Main {
            const val A_MAIN = "/main/MainActivity"
            const val F_LEARN = "/main/LearnFragment"
            const val F_PROGRESS = "/main/ProgressFragment"
            const val F_MY = "/main/MyFragment"
        }

        object Detail {
            const val A_DETAIL = "/main/DetailActivity"
        }

        object Login {
            const val F_LOGIN = "/login/LoginFragment"
            const val F_REGISTER = "/login/RegisterFragment"
        }

        object Learn {
            const val F_AUDIO = "/learn/AudioFragment"
            const val F_VIDEO = "/learn/VideoFragment"
            const val F_CLASS = "/learn/ClassFragment"
        }

        object Progress {
            const val F_STUDENT_TRACK = "/progress/StudentTrackFragment"
            const val F_STUDENT_DETAIL = "/progress/StudentDetailFragment"
        }

        object My {
            const val F_REGISTRATION = "/my/StudentRegisterFragment"
            const val F_CREATE_CLASS = "/my/CreateClassFragment"
            const val F_CHOOSE_CLASS = "/my/ChooseClassFragment"
            const val F_PROGRESS_TRACK = "/my/ProgressTrackFragment"
        }

        object ClassManage {
            const val F_CLASS_MANAGE = "/class/ClassManageFragment"
            const val F_SIGN_IN = "/class/SignInFragment"
            const val F_CLASS_DETAIL = "/class/ClassDetailFragment"
            const val F_COURSE_LIST = "/class/CourseListFragment"
        }
    }
}