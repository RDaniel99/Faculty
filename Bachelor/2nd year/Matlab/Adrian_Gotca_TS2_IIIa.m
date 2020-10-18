function y=next(camera)
    set=0;
    if (camera==46)
       y=-1;
    else 
        for i=1:size(G(camera))
            if (next(G(camera,i)==1))
                y=G(camera,i);
                set=1;
            end
        end
        if (set==0)
           y=0; 
        end
    end
end