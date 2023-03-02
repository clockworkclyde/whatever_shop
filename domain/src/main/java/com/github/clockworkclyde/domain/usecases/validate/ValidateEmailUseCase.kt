package com.github.clockworkclyde.domain.usecases.validate

import android.util.Patterns
import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.domain.usecases.IUseCase
import com.github.clockworkclyde.domain.R
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
   private val resources: IResourcesProvider
) : IValidateEmailUseCase {

   override suspend fun invoke(param: String): String? {
      return when {
         param.isEmpty() -> {
            resources.getString(R.string.empty_input_error)
         }
         !Patterns.EMAIL_ADDRESS.matcher(param).matches() -> {
            resources.getString(R.string.invalid_email_error)
         }
         else -> null
      }
   }
}

interface IValidateEmailUseCase: IUseCase.SingleInOut<String, String?>