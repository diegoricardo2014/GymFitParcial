import android.util.Log
import pe.edu.upc.gymfit.ui.main.blog.state.BlogStateEvent
import pe.edu.upc.gymfit.ui.main.blog.state.BlogStateEvent.*
import pe.edu.upc.gymfit.ui.main.blog.state.BlogViewState
import pe.edu.upc.gymfit.ui.main.blog.viewmodel.BlogViewModel
import pe.edu.upc.gymfit.ui.main.blog.viewmodel.setBlogListData
import pe.edu.upc.gymfit.ui.main.blog.viewmodel.setQuery
import pe.edu.upc.gymfit.ui.main.blog.viewmodel.setQueryExhausted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
fun BlogViewModel.resetPage(){
    val update = getCurrentViewStateOrNew()
    update.blogFields.page = 1
    setViewState(update)
}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
fun BlogViewModel.refreshFromCache(){
    if(!isJobAlreadyActive(BlogSearchEvent())){
        setQueryExhausted(false)
        setStateEvent(BlogSearchEvent(false))
    }
}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
fun BlogViewModel.loadFirstPage() {
    if(!isJobAlreadyActive(BlogSearchEvent())){
        setQueryExhausted(false)
        resetPage()
        setStateEvent(BlogSearchEvent())
        Log.e(TAG, "BlogViewModel: loadFirstPage: ${viewState.value!!.blogFields.searchQuery}")
    }
}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
private fun BlogViewModel.incrementPageNumber(){
    val update = getCurrentViewStateOrNew()
    val page = update.copy().blogFields.page ?: 1
    update.blogFields.page = page.plus(1)
    setViewState(update)
}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
fun BlogViewModel.nextPage(){
    if(!isJobAlreadyActive(BlogSearchEvent())
        && !viewState.value!!.blogFields.isQueryExhausted!!
    ){
        Log.d(TAG, "BlogViewModel: Attempting to load next page...")
        incrementPageNumber()
        setStateEvent(BlogSearchEvent())
    }
}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
fun BlogViewModel.handleIncomingBlogListData(viewState: BlogViewState){
    viewState.blogFields.let { blogFields ->
        blogFields.blogList?.let { setBlogListData(it) }
    }
}





