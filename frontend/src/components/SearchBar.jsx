export default function SearchBar({ value, onChange }) {
  return (
    <label className="search-control">
      <span className="visually-hidden">Search tasks</span>
      <input
        type="search"
        className="search-input"
        placeholder="Search tasks..."
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </label>
  );
}
