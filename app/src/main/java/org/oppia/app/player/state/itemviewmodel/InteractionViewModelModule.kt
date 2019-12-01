package org.oppia.app.player.state.itemviewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import org.oppia.app.player.state.listener.PreviousNavigationButtonListener

/**
 * Module to provide interaction view model-specific dependencies for intreactions that should be explicitly displayed
 * to the user.
 */
@Module
class InteractionViewModelModule {
  // TODO(#300): Use a common source for these interaction IDs to de-duplicate them from other places in the codebase
  //  where they are referenced.
  @Provides
  @IntoMap
  @StringKey("Continue")
  fun provideContinueInteractionViewModelFactory(fragment: Fragment): InteractionViewModelFactory {
    return { _, _, interactionAnswerReceiver, hasPreviousButton ->
      ContinueInteractionViewModel(
        interactionAnswerReceiver, hasPreviousButton, fragment as PreviousNavigationButtonListener
      )
    }
  }

  @Provides
  @IntoMap
  @StringKey("MultipleChoiceInput")
  fun provideMultipleChoiceInputViewModelFactory(): InteractionViewModelFactory {
    return { explorationId, interaction, interactionAnswerReceiver, _ ->
      SelectionInteractionViewModel(explorationId, interaction, interactionAnswerReceiver)
    }
  }

  @Provides
  @IntoMap
  @StringKey("ItemSelectionInput")
  fun provideItemSelectionInputViewModelFactory(): InteractionViewModelFactory {
    return { explorationId, interaction, interactionAnswerReceiver, _ ->
      SelectionInteractionViewModel(explorationId, interaction, interactionAnswerReceiver)
    }
  }

  @Provides
  @IntoMap
  @StringKey("FractionInput")
  fun provideFractionInputViewModelFactory(context: Context): InteractionViewModelFactory {
    return { _, interaction, _, _ -> FractionInteractionViewModel(interaction, context) }
  }

  @Provides
  @IntoMap
  @StringKey("NumericInput")
  fun provideNumericInputViewModelFactory(): InteractionViewModelFactory {
    return { _, _, _, _ -> NumericInputViewModel() }
  }

  @Provides
  @IntoMap
  @StringKey("TextInput")
  fun provideTextInputViewModelFactory(): InteractionViewModelFactory {
    return { _, interaction, _, _ -> TextInputViewModel(interaction) }
  }
}
