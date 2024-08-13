//
//  HomeScreen.swift
//  iosApp
//
//  Created by Adrià Fernández Arans on 13/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

extension Movie: Identifiable { }

import SwiftUI
import ComposeApp

struct HomeScreen: View {
    var viewModel = HomeViewModel()
    
    var body: some View {
        NavigationView{
            VStack {
                Observing(viewModel.state){ state in
                    if (state.isLoading) {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle())
                    }
                    if (!state.movies.isEmpty) {
                        let columns = [
                            GridItem(.flexible()),
                            GridItem(.flexible())
                        ]
                        
                        ScrollView{
                            LazyVGrid(columns: columns){
                                ForEach(state.movies) { movie in
                                    NavigationLink(destination:
                                        DetailScreen(movieId: movie.id)
                                    ){
                                        MovieItemView(movie: movie)
                                    }
                                }
                            }.padding()
                        }
                    }
                }
            }
            .navigationTitle(Text("KMPlayground"))
        }.onAppear {
            viewModel.onEvent(event: HomeViewModel.UiEventOnUiReady())
        }
        
    }
}

struct MovieItemView: View {
    var movie: Movie
    
    var body: some View {
        VStack {
            GeometryReader { geometry in
                ZStack(alignment: .topTrailing){
                    AsyncImage(url: URL(string: movie.poster)) { phase in
                        switch phase {
                        case .empty:
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle())
                                .frame(width: geometry.size.width, height: geometry.size.height)
                        case .success(let image):
                            image
                                .resizable()
                                .aspectRatio(2 / 3, contentMode: .fill)
                                .frame(width: geometry.size.width, height: geometry.size.height)
                                .clipped()
                                .cornerRadius(8)
                        case .failure:
                            Image(systemName: "photo")
                                .resizable()
                                .aspectRatio(2 / 3, contentMode: .fill)
                                .frame(width: geometry.size.width, height: geometry.size.height)
                                .clipped()
                                .cornerRadius(8)
                        @unknown default:
                            EmptyView()
                        }
                    }
                    if (movie.isFavorite) {
                        Image(systemName: "heart.fill")
                            .foregroundColor(.white)
                            .padding(8)
                    }
                }
            }
            .aspectRatio(2/3, contentMode: .fit)
            
            Text(movie.title)
                .font(.caption)
                .tint(Color.black)
                .lineLimit(1)
                .padding(5)
        }
    }
}
