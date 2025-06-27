document.addEventListener('DOMContentLoaded', function() {
    const tripsContainer = document.getElementById('trips-container');
    const applyFiltersBtn = document.getElementById('apply-filters');
    const resetFiltersBtn = document.getElementById('reset-filters');
    
    if (!tripsContainer || !applyFiltersBtn || !resetFiltersBtn) {
        console.error('Required elements not found');
        return;
    }
    
    // API Configuration
    const API_BASE_URL = 'http://localhost:8080';
    const TRIPS_ENDPOINT = '/getTrips';
    
    // Global variable to store all trips
    let allTrips = [];
    
    // Fetch trips from API
    async function fetchTrips() {
        try {
            tripsContainer.innerHTML = '<div class="loading-spinner"><i class="fas fa-spinner fa-spin"></i> Loading trips...</div>';
            
            const response = await fetch(`${API_BASE_URL}${TRIPS_ENDPOINT}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const trips = await response.json();
            allTrips = Array.isArray(trips) ? trips : [];
            return allTrips;
        } catch (error) {
            console.error('Error fetching trips:', error);
            tripsContainer.innerHTML = '<div class="error-message">Error loading trips. Please try again later.</div>';
            return [];
        }
    }
    
    // Format date for display
    function formatDate(dateString) {
        try {
            const options = { year: 'numeric', month: 'short', day: 'numeric' };
            return new Date(dateString).toLocaleDateString('en-US', options);
        } catch (e) {
            console.warn('Invalid date format:', dateString);
            return dateString; // Return original if can't format
        }
    }
    
    // Render trips to the DOM
    function renderTrips(trips) {
        if (!trips || !Array.isArray(trips)) {
            tripsContainer.innerHTML = '<div class="error-message">Invalid trips data</div>';
            return;
        }
        
        if (trips.length === 0) {
            tripsContainer.innerHTML = '<div class="no-results">No trips found matching your criteria.</div>';
            return;
        }
        
        tripsContainer.innerHTML = trips.map(trip => `
            <div class="destination-card" data-id="${trip.id || ''}">
                <div class="destination-img">
                    <img src="${trip.imageUrl || 'https://images.unsplash.com/photo-1503917988258-f87a78e3c995?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80'}" 
                         alt="${trip.destination || 'Travel destination'}">
                    <div class="availability-badge ${trip.availablePlaces < 10 ? 'low-availability' : ''}">
                        ${trip.availablePlaces < 10 ? 'Few spots left' : 'Available'}
                    </div>
                </div>
                <div class="destination-info">
                    <h3>${trip.title || trip.destination || 'Unnamed Destination'}</h3>
                    <p class="destination-description">${trip.description || 'Experience this amazing destination with our expertly crafted tour.'}</p>
                    
                    <div class="destination-meta">
                        ${trip.destination ? `<span><i class="fas fa-map-marker-alt"></i> ${trip.destination}</span>` : ''}
                        ${trip.departureLocation ? `<span><i class="fas fa-plane-departure"></i> ${trip.departureLocation}</span>` : ''}
                        ${trip.departureDate ? `<span><i class="fas fa-calendar-alt"></i> ${formatDate(trip.departureDate)}</span>` : ''}
                        ${trip.returnDate ? `<span><i class="fas fa-calendar-check"></i> ${formatDate(trip.returnDate)}</span>` : ''}
                        ${trip.durationDays ? `<span><i class="fas fa-clock"></i> ${trip.durationDays} days</span>` : ''}
                        ${trip.transportType ? `<span><i class="fas fa-bus"></i> ${trip.transportType}</span>` : ''}
                    </div>
                    
                    ${trip.hotelName ? `
                    <div class="hotel-info">
                        <span><i class="fas fa-hotel"></i> ${trip.hotelName}</span>
                    </div>` : ''}
                    
                    ${trip.includesVisa ? '<div class="visa-included"><i class="fas fa-passport"></i> Visa included</div>' : ''}
                    
                    <div class="destination-price">$${(trip.pricePerPerson || 0).toFixed(2)} per person</div>
                    
                    <a href="#" class="btn" aria-label="Book ${trip.destination || 'this trip'}">Book Now</a>
                </div>
            </div>
        `).join('');
    }
    
    // Filter trips based on user selection
    function filterTrips() {
        const destinationFilter = document.getElementById('filter-destination').value.toLowerCase();
        const departureFilter = document.getElementById('filter-departure').value.toLowerCase();
        let minPrice = parseFloat(document.getElementById('filter-price-min').value) || 0;
        let maxPrice = parseFloat(document.getElementById('filter-price-max').value) || Infinity;
        const durationFilter = parseInt(document.getElementById('filter-duration').value) || 0;
        const transportFilter = document.getElementById('filter-transport').value;
        
        // Validate price range
        if (minPrice > maxPrice) {
            [minPrice, maxPrice] = [maxPrice, minPrice]; // Swap values if min > max
        }
        
        const filteredTrips = allTrips.filter(trip => {
            // Destination filter
            if (destinationFilter && 
                !(trip.destination || '').toLowerCase().includes(destinationFilter)) {
                return false;
            }
            
            // Departure location filter
            if (departureFilter && 
                !(trip.departureLocation || '').toLowerCase().includes(departureFilter)) {
                return false;
            }
            
            // Price filter
            const price = trip.pricePerPerson || 0;
            if (price < minPrice || price > maxPrice) {
                return false;
            }
            
            // Duration filter
            const duration = trip.durationDays || 0;
            if (durationFilter > 0 && duration > durationFilter) {
                return false;
            }
            
            // Transport type filter
            if (transportFilter && (trip.transportType || '') !== transportFilter) {
                return false;
            }
            
            return true;
        });
        
        renderTrips(filteredTrips);
    }
    
    // Reset all filters
    function resetFilters() {
        document.getElementById('filter-destination').value = '';
        document.getElementById('filter-departure').value = '';
        document.getElementById('filter-price-min').value = '';
        document.getElementById('filter-price-max').value = '';
        document.getElementById('filter-duration').value = '0';
        document.getElementById('filter-transport').value = '';
        
        renderTrips(allTrips);
    }
    
    // Initialize the page
    async function initialize() {
        const trips = await fetchTrips();
        
        // Event listeners
        applyFiltersBtn.addEventListener('click', filterTrips);
        resetFiltersBtn.addEventListener('click', resetFilters);
        
        // Initial render
        renderTrips(trips);
    }
    
    // Start the application
    initialize();
});