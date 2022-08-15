package com.czl.lib_base.config

/**
 * @author Alwyn
 * @Date 2020/10/22
 * @Description 常量管理类
 */
interface AppConstants {
    object Common {
        const val PAGE_SIZE = 20

    }

    object BundleKey {
        const val KEY_STRING = "keyString"

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
            const val A_DETAIL = "/main/DetailActivity"
            const val F_CREATE_CLASS = "/main/CreateClassFragment"

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

        }

        object My {
            const val F_REGISTRATION = "/my/StudentRegisterFragment"
        }
    }
}