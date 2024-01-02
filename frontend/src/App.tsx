import {createSignal, For, Show} from 'solid-js'
import {createStore} from "solid-js/store";

interface ArtistSearchQuery {
  name: string
}

interface ArtistSearchResult {
  name: string
  id: string
  image: string
}

interface ArtistSearchResults {
  results: ArtistSearchResult[]
}

interface FeatureSearchArtist {
  id: string
  name: string
  image: string
}

interface FeatureSearchQuery {
  artists: FeatureSearchArtist[]
}

interface FeatureSearchResult {
  id: string
  name: string
}

interface FeatureSearchResults {
  results: FeatureSearchResult[]
}

function App() {
  const [featureSearchQuery, setFeatureSearchQuery] = createSignal<FeatureSearchQuery>({ artists: [] })
  const [featureResults, setFeatureResults] = createStore<FeatureSearchResults>({ results: [] })

  const [artistSearchQuery, setArtistSearchQuery] = createSignal<ArtistSearchQuery>({ name: '' })
  const [artistResults, setArtistResults] = createStore<ArtistSearchResults>({ results: [] })

  const searchForArtists = async () => {

    const response = await fetch(`http://localhost:8080/search/artist?artistName=${artistSearchQuery().name}`)
    const results: ArtistSearchResults = await response.json()
    console.log(results)
    setArtistResults(results)
  }

  const selectArtist = (artist: ArtistSearchResult) => {
    setFeatureSearchQuery({ artists: [...featureSearchQuery().artists, { id: artist.id, name: artist.name, image: artist.image }] })
    setArtistSearchQuery({ name: '' })
    setArtistResults("results", [])
  }

  const searchForFeatures = async () => {
    const artists = featureSearchQuery().artists.map(artist => {
      return { id: artist.id, name: artist.name }
    })
    const queryBody = { artists }
    const response = await fetch(`http://localhost:8080/search/features`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(queryBody)
    })
    const results: FeatureSearchResults = await response.json()
    setFeatureResults(results)
  }

  return (
    <>
      <h1>Spotify Feature Finder</h1>
      <h2>Artists to Search</h2>
      <div>
        <For each={featureSearchQuery().artists}>
          {artist => <div>{artist.name}</div>}
        </For>
        <button onClick={() => { searchForFeatures() }}>Search Features</button>
      </div>
      <br/>
      {/*<pre>{JSON.stringify(featureResults, null, 2)}</pre>*/}
      <Show when={featureResults.results.length > 0}>
        <h2>Results</h2>
        <div>
          <For each={featureResults.results}>
            {result => <div>{result.name}</div>}
          </For>
        </div>
      </Show>
      <br/>
      <h2>Search for Artists</h2>
      <div>
        <form onSubmit={(e) => { e.preventDefault() ; searchForArtists() }}>
          <input type="text" value={artistSearchQuery().name} oninput={e => setArtistSearchQuery({ name: e.currentTarget.value })} />
          <button type="submit">Search Artists</button>
        </form>
      </div>
      <div>
        <For each={artistResults.results}>
          {artist => <div onClick={() => { selectArtist(artist) }}>{artist.name}</div>}
        </For>
      </div>
      {/*<pre>{JSON.stringify(artistResults, null, 2)}</pre>*/}
    </>
  )
}

export default App
