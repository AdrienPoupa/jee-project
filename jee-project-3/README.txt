# jee-project

# Information

 - We use a stylesheet file "style.css" put in "web/"

- About the instanciation of the entity manager, we noticed that there is little bug with the deployment made by netbeans.
It appears that the entity manager is incorrectly closed before redeployment. We had to create a new class named EFM which 
implements ServletContextListener to properly open and close entity manager when the context is created or closed.

- Instead of using radio button on the members list, we decided to use checkbox. By this way, it's possible to select and delete
several members in the same time.