//
//  DetailScreen.swift
//  iosApp
//
//  Created by Adrià Fernández Arans on 13/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct DetailScreen: View {
   // @StateObject var viewModelStoreOwner:
     //   SharedViewModelStoreOwner<DetailViewModel>
    var viewModel: DetailViewModel
    
    init(movieId: Int32){
        viewModel = DetailViewModel(movieId: movieId)
     //   self._viewModelStoreOwner =
       //     StateObject(
         //       wrappedValue: SharedViewModelStoreOwner(DetailViewModel(movieId: movieId))
           // )
    }
    
    var body: some View {
        Observing(viewModel.state) { state in
            VStack {
                if(state.isLoading) {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                }
                
                if let movieDetail = state.movie {
                    MovieDetail(
                        movie: movieDetail,
                        onFavoriteClick: { viewModel.onEvent(event: DetailViewModel.UiEventOnFavouriteClicked()) }
                    )
                }
            }
        }
    }
}

struct MovieDetail: View {
    var movie: Movie
    var onFavoriteClick: () -> Void
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                if let backdrop = movie.backdrop {
                    AsyncImage(url: URL(string: backdrop)) { image in
                        image
                            .resizable()
                            .frame(maxWidth: .infinity)
                    } placeholder: {
                        ProgressView()
                    }
                    .aspectRatio(16 / 9, contentMode: .fill)
                    .frame(maxHeight: 200)
                    .clipped()
                }
                Text(movie.overview)
                    .padding()
                
                VStack(alignment: .leading, spacing: 8) {
                    Text("**Original language**: \(movie.originalLanguage)")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text("**Original title**: \(movie.originalTitle)")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text("**Release date**: \(movie.releaseDate)")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text("**Popularity**: \(movie.popularity)")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text("**Vote average**: \(movie.voteAverage)")
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                .padding()
                .background(Color.secondary.opacity(0.1))
                .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .leading)
                .cornerRadius(8)
            }
        }
        .navigationTitle(movie.title)
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button(action: onFavoriteClick) {
                    Image(systemName: movie.isFavorite ? "heart.fill" : "heart")
                }
            }
        }
    }
}
