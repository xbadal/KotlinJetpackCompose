package com.badal.reciepeapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.badal.reciepeapp.model.CategoryModel
import com.badal.reciepeapp.view_model.MainViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun RecipeScreen(modifier: Modifier = Modifier,
                 viewState:MainViewModel.RecipeState,
                 navigateToDetails: (CategoryModel) -> Unit) {

    val recipeViewModel: MainViewModel = viewModel()


    Box(modifier = Modifier.fillMaxSize()) {

        when {
            viewState.loading -> {
                CircularProgressIndicator(
                    modifier = modifier.align(Alignment.Center)
                )
            }

            viewState.error != null -> {

                Text(text = "Error Occured ${viewState.error}")
            }

            else -> {
                // Display Categories
                CategoryScreen(viewState.list, navigateToDetails)

            }
        }
    }


}


@Composable
fun CategoryScreen(categories: List<CategoryModel>, navigateToDetails: (CategoryModel) -> Unit) {

    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {

        items(categories) { category ->
            CategoryItem(category, navigateToDetails)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CategoryItem(
    category: CategoryModel,
    navigateToDetails: (CategoryModel) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                navigateToDetails(category)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GlideImage(
            model = "${category.strCategoryThumb}",
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )

        Text(
            text = "${category.strCategory}",
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(4.dp)
        )
    }
}