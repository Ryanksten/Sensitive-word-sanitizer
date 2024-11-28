import React, { useState, useEffect } from 'react';

const API_BASE_URL = 'http://localhost:8080/api/sensitive-words';

function SensitiveWordsApp() {
  const [inputText, setInputText] = useState('');
  const [sanitizedText, setSanitizedText] = useState('');
  const [sensitiveWords, setSensitiveWords] = useState([]);
  const [newWord, setNewWord] = useState('');
  const [editingWord, setEditingWord] = useState(null);

  useEffect(() => {
    fetchSensitiveWords();
  }, []);

  const fetchSensitiveWords = async () => {
    try {
      const response = await fetch(API_BASE_URL);
      const words = await response.json();
      setSensitiveWords(words);
    } catch (error) {
      console.error('Error fetching sensitive words:', error);
    }
  };

  const handleSanitize = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/sanitize`, {
        method: 'POST',
        body: inputText,
        headers: {
          'Content-Type': 'text/plain'
        }
      });
      const sanitized = await response.text();
      setSanitizedText(sanitized);
    } catch (error) {
      console.error('Error sanitizing text:', error);
    }
  };

  const addSensitiveWord = async () => {
    if (!newWord.trim()) return;

    try {
      const response = await fetch(API_BASE_URL, {
        method: 'POST',
        body: newWord.trim(),
        headers: {
          'Content-Type': 'text/plain'
        }
      });
      await response.json();
      setNewWord('');
      fetchSensitiveWords();
    } catch (error) {
      console.error('Error adding sensitive word:', error);
    }
  };

  const deleteSensitiveWord = async (id) => {
    try {
      await fetch(`${API_BASE_URL}/${id}`, { method: 'DELETE' });
      fetchSensitiveWords();
    } catch (error) {
      console.error('Error deleting sensitive word:', error);
    }
  };

  const updateSensitiveWord = async () => {
    if (!editingWord || !editingWord.word.trim()) return;

    try {
      await fetch(`${API_BASE_URL}/${editingWord.id}`, {
        method: 'PUT',
        body: editingWord.word.trim(),
        headers: {
          'Content-Type': 'text/plain'
        }
      });
      setEditingWord(null);
      fetchSensitiveWords();
    } catch (error) {
      console.error('Error updating sensitive word:', error);
    }
  };

  return (
    <div style={{ maxWidth: '600px', margin: 'auto', padding: '20px' }}>
      <div style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '20px' }}>
        <h1 style={{ fontSize: '1.5rem', marginBottom: '20px' }}>Sensitive Word Sanitizer</h1>
        
        {/* Sanitization Section */}
        <div style={{ marginBottom: '20px' }}>
          <h2 style={{ fontSize: '1.2rem', marginBottom: '10px' }}>Sanitize Text</h2>
          <input 
            value={inputText}
            onChange={(e) => setInputText(e.target.value)}
            placeholder="Enter text to sanitize"
            style={{ width: '100%', padding: '10px', marginBottom: '10px' }}
          />
          <button 
            onClick={handleSanitize}
            style={{ width: '100%', padding: '10px', backgroundColor: '#007bff', color: 'white', border: 'none' }}
          >
            Sanitize
          </button>
          {sanitizedText && (
            <div style={{ marginTop: '10px', padding: '10px', backgroundColor: '#f8f9fa', borderRadius: '4px' }}>
              <strong>Sanitized Text:</strong> {sanitizedText}
            </div>
          )}
        </div>

        {/* Sensitive Words Management */}
        <div>
          <h2 style={{ fontSize: '1.2rem', marginBottom: '10px' }}>Manage Sensitive Words</h2>
          <div style={{ display: 'flex', marginBottom: '10px' }}>
            <input 
              value={newWord}
              onChange={(e) => setNewWord(e.target.value)}
              placeholder="Enter a new sensitive word"
              style={{ flexGrow: 1, padding: '10px', marginRight: '10px' }}
            />
            <button 
              onClick={addSensitiveWord}
              style={{ padding: '10px', backgroundColor: '#28a745', color: 'white', border: 'none' }}
            >
              Add
            </button>
          </div>
          
          {/* Sensitive Words List */}
          <div style={{ border: '1px solid #ddd', borderRadius: '4px' }}>
            {sensitiveWords.length === 0 ? (
              <p style={{ padding: '10px', color: '#6c757d' }}>No sensitive words added yet</p>
            ) : (
              sensitiveWords.map((word, index) => (
                <div 
                  key={index} 
                  style={{ 
                    display: 'flex', 
                    justifyContent: 'space-between', 
                    alignItems: 'center', 
                    padding: '10px', 
                    borderBottom: '1px solid #eee' 
                  }}
                >
                  {editingWord?.id === index ? (
                    <input 
                      value={editingWord.word}
                      onChange={(e) => setEditingWord({...editingWord, word: e.target.value})}
                      style={{ flexGrow: 1, marginRight: '10px', padding: '5px' }}
                    />
                  ) : (
                    <span>{word}</span>
                  )}
                  <div>
                    {editingWord?.id === index ? (
                      <button 
                        onClick={updateSensitiveWord}
                        style={{ marginRight: '10px', padding: '5px 10px', backgroundColor: '#ffc107' }}
                      >
                        Save
                      </button>
                    ) : (
                      <button 
                        onClick={() => setEditingWord({id: index, word})}
                        style={{ marginRight: '10px', padding: '5px 10px', backgroundColor: '#17a2b8', color: 'white' }}
                      >
                        Edit
                      </button>
                    )}
                    <button 
                      onClick={() => deleteSensitiveWord(index)}
                      style={{ padding: '5px 10px', backgroundColor: '#dc3545', color: 'white' }}
                    >
                      Delete
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default SensitiveWordsApp;