package lila.rating

import lila.core.user.LightPerf
import lila.core.user.WithPerf
import lila.core.perf.{ UserPerfs, UserWithPerfs }
import lila.rating.PerfExt.*
import lila.rating.UserPerfsExt.bestRating

object UserWithPerfs:

  extension (p: UserWithPerfs)
    def usernameWithBestRating = s"${p.username} (${p.perfs.bestRating})"
    def hasVariantRating       = lila.rating.PerfType.variants.exists(p.perfs.apply(_).nonEmpty)
    def titleUsernameWithBestRating =
      p.title.fold(p.usernameWithBestRating): t =>
        s"$t ${p.usernameWithBestRating}"
    def lightPerf(key: PerfKey) =
      val perf = p.perfs(key)
      LightPerf(p.light, key, perf.intRating, perf.progress)
    def only(pt: PerfType) = WithPerf(p.user, p.perfs(pt.key))

  def apply(user: User, perfs: Option[UserPerfs]): UserWithPerfs =
    new UserWithPerfs(user, perfs | lila.rating.UserPerfs.default(user.id))
  given UserIdOf[UserWithPerfs] = _.user.id
